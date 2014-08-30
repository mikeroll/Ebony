package vu.co.mikeroll.ebony.app

import android.widget.BaseAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.content.Context
import android.widget.BaseExpandableListAdapter
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.SimpleAdapter
import vu.co.mikeroll.ebony.data.TodoList

public class TodoListAdapter(val context: Context, var tasks: TodoList) : BaseAdapter() {

    override fun getCount(): Int {
        return tasks.count()
    }

    override fun getItem(position: Int): Any? {
        return tasks[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false)
        }
        (view?.findViewById(R.id.todo_text) as EditText?)?.setText(tasks[position].content)
        return view
    }
}
