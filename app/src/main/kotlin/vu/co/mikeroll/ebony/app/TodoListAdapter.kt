package vu.co.mikeroll.ebony.app

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import vu.co.mikeroll.ebony.data.TodoList
import android.widget.ArrayAdapter
import vu.co.mikeroll.ebony.data.TodoItem

public class TodoListAdapter(context: Context, val tasks: TodoList)
             : ArrayAdapter<TodoItem>(context, R.layout.todo_item, R.id.todo_text, tasks) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(getContext()!!).inflate(R.layout.todo_item, parent, false)
        }
        (view!!.findViewById(R.id.todo_text) as EditText).setText(tasks[position].content)
        return view
    }
}
