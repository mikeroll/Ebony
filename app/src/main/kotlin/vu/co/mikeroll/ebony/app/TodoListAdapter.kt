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
import vu.co.mikeroll.ebony.db.Todos

public class TodoListAdapter(val ctx: Context) : ResourceCursorAdapter(ctx, R.layout.todo_item, null), UndoAdapter {

    class ViewHolder(v: View) {
        val contentView: TextView
        val importantView: CheckBox
        {
            contentView = v.findViewById(R.id.todo_text) as TextView
            importantView = v.findViewById(R.id.todo_important_btn) as CheckBox
        }
    }

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        val v = super<ResourceCursorAdapter>.newView(context, cursor, parent)
        v.setTag(ViewHolder(v))
        return v
    }

    override fun bindView(view: View, context: Context?, cursor: Cursor) {
        val content = cursor.getString(cursor.getColumnIndex(Todos.KEY_CONTENT))
        val important = cursor.getInt(cursor.getColumnIndex(Todos.KEY_IS_IMPORTANT)) != 0

        val holder = view.getTag() as ViewHolder
        holder.contentView.setText(content)
        holder.importantView.setChecked(important)
    }

    // UndoAdapter implementation
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
