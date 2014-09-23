package vu.co.mikeroll.ebony.app

import android.widget.ResourceCursorAdapter
import android.content.Context
import android.view.View
import android.database.Cursor
import android.widget.CheckBox
import android.widget.TextView
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoAdapter
import android.view.ViewGroup
import android.view.LayoutInflater

public class TodoListAdapter(val ctx: Context) : ResourceCursorAdapter(ctx, R.layout.todo_item, null), UndoAdapter {
    override fun bindView(view: View, context: Context?, cursor: Cursor) {
        val content = cursor.getString(1)
        (view.findViewById(R.id.todo_text) as TextView?)?.setText(content)
        val isImportant = cursor.getInt(2) == 1
        (view.findViewById(R.id.todo_important_btn) as CheckBox?)?.setChecked(isImportant)
    }
    override fun getUndoView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.todo_item_undo, parent, false)
        }
        return view
    }
    override fun getUndoClickView(view: View?): View? {
        return view?.findViewById(R.id.undo_btn)
    }
}
