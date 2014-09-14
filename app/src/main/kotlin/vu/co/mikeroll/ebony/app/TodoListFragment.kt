package vu.co.mikeroll.ebony.app

import android.os.Bundle
import android.app.ListFragment
import vu.co.mikeroll.ebony.db.Database
import android.os.AsyncTask
import android.database.Cursor
import vu.co.mikeroll.ebony.db.TodoItem

public class TodoListFragment() : ListFragment() {

    var adapter: TodoListAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val ctx = getActivity()!!
        Database.connect(ctx)
        adapter = TodoListAdapter(ctx)
        setListAdapter(adapter)
        load()
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
