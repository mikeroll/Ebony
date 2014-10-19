package vu.co.mikeroll.ebony.app

import android.app.Fragment
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ListView

import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter

import vu.co.mikeroll.ebony.db.Database
import vu.co.mikeroll.ebony.db.TodoItem
import vu.co.mikeroll.ebony.appwidget.EbonyWidgetProvider

public class TodoListFragment() : Fragment() {

    private var activity: Context? = null
    private var listView: ListView? = null
    private var adapter: TodoListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_todo_list, container, false)
        listView = v?.findViewById(R.id.todo_list) as ListView?
        setHasOptionsMenu(true)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity()
        Database.connect(activity!!)
        adapter = TodoListAdapter(activity!!)
        val swipeAdapter = SimpleSwipeUndoAdapter(adapter!!, activity!!, onDeleteCallback)
        swipeAdapter.setAbsListView(listView)
        listView?.setAdapter(swipeAdapter)
        listView?.setOnItemClickListener { (lv, v, pos, id) -> open(lv, pos) }
        load()
    }

    override fun onCreateOptionsMenu(menu : Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.ebony_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item : MenuItem?) : Boolean {
        when (item?.getItemId()) {
            R.id.action_settings -> return true
            R.id.action_new -> { new(); return true }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showItem(item: TodoItem) {
        val dialog = TodoDialogFragment.new(item)
        dialog.onUpdate = { (item) -> save(item) }
        dialog.show(getFragmentManager()!!, "todoDialog")
    }

    fun new() = showItem(TodoItem("", false))

    fun open(lv: AdapterView<out Adapter?>, pos: Int) {
        val row = lv.getItemAtPosition(pos) as Cursor
        showItem(TodoItem(row))
    }

    fun load() = object : AsyncTask<Void, Void, Cursor>() {
        override fun doInBackground(vararg params: Void?): Cursor? {
            return Database.selectAll()
        }
        override fun onPostExecute(result: Cursor) {
            adapter?.changeCursor(result)
            updateWidgets()
        }
    }.execute()

    fun save(item: TodoItem) = object : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg voids: Void?): Void? {
            Database.upsert(item)
            return null
        }
        override fun onPostExecute(result: Void?) {
            load()
        }
    }.execute()

    fun delete(ids: Array<Long>) = object : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg voids: Void?): Void? {
            Database.delete(ids)
            return null
        }
        override fun onPostExecute(result: Void?) {
            load()
        }
    }.execute()

    val onDeleteCallback = object : OnDismissCallback {
        override fun onDismiss(lv: ViewGroup?, pos: IntArray?) {
            val ids = Array(pos!!.size, { p -> adapter!!.getItemId(pos[p]) })
            adapter!!.swapCursor(SwipeCursorWrapper(adapter!!.getCursor()!!, pos))
            delete(ids)
        }
    }

    fun updateWidgets() {
        val appContext = activity!!.getApplicationContext()!!
        val widgetManager = AppWidgetManager.getInstance(appContext)!!
        val ids = widgetManager.getAppWidgetIds(ComponentName(appContext, javaClass<EbonyWidgetProvider>()))
        widgetManager.notifyAppWidgetViewDataChanged(ids, R.id.widget_todo_list)
    }
}
