package vu.co.mikeroll.ebony.app

import android.os.Bundle
import vu.co.mikeroll.ebony.db.Database
import android.os.AsyncTask
import android.database.Cursor
import vu.co.mikeroll.ebony.db.TodoItem
import android.app.Fragment
import android.widget.ListView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.AdapterView
import android.widget.Adapter

public class TodoListFragment() : Fragment() {

    var listView : ListView? = null
    var adapter: TodoListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_todo_list, container, false)
        listView = v?.findViewById(R.id.todo_list) as ListView?
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val ctx = getActivity()!!
        Database.connect(ctx)
        adapter = TodoListAdapter(ctx)
        listView?.setAdapter(adapter!!)
        listView?.setOnItemClickListener { (lv, v, pos, id) -> showTodoDialog(lv, pos) }
        load()
    }

    fun showTodoDialog(lv: AdapterView<out Adapter?>, pos: Int) {
        val row = lv.getItemAtPosition(pos) as Cursor
        val item = TodoItem(row)
        val dialog = TodoDialogFragment.new(item)
        dialog.onUpdate = { (item) -> save(item) }
        dialog.show(getFragmentManager()!!, "todoDialog")
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
    }.execute(item)
}
