package vu.co.mikeroll.ebony.app

import android.app.Fragment
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

public class TodoListFragment() : Fragment() {

    var listView : ListView? = null
    var adapter: TodoListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_todo_list, container, false)
        listView = v?.findViewById(R.id.todo_list) as ListView?
        setHasOptionsMenu(true)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val ctx = getActivity()!!
        Database.connect(ctx)
        adapter = TodoListAdapter(ctx)
        val swipeAdapter = SimpleSwipeUndoAdapter(adapter!!, getActivity()!!, onDeleteCallback)
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
        }
    }.execute()

    fun save(item: TodoItem) = object : AsyncTask<TodoItem, Void, Void>() {
        override fun doInBackground(vararg items: TodoItem?): Void? {
            Database.upsert(items[0]!!)
            return null
        }
        override fun onPostExecute(result: Void) {
            load()
        }
    }.execute(item)

    fun delete(id: Long) = object : AsyncTask<Long, Void, Void>() {
        override fun doInBackground(vararg ids: Long?): Void? {
            Database.delete(id)
            return null
        }
        override fun onPostExecute(result: Void) {
            load()
        }
    }.execute(id)

    val onDeleteCallback = object : OnDismissCallback {
        override fun onDismiss(lv: ViewGroup?, pos: IntArray?) {
            val id = (adapter?.getItem(pos!![0]) as Cursor).getLong(0)
            delete(id)
        }
    }
}
