package vu.co.mikeroll.ebony.app

import android.widget.ResourceCursorAdapter
import android.content.Context
import android.view.View
import android.database.Cursor
import android.widget.CheckBox
import android.widget.TextView

public class TodoListAdapter(ctx: Context) : ResourceCursorAdapter(ctx, R.layout.todo_item, null) {
    override fun bindView(view: View, context: Context?, cursor: Cursor) {
        val content = cursor.getString(1)
        (view.findViewById(R.id.todo_text) as TextView?)?.setText(content)
        val isImportant = cursor.getInt(2) == 1
        (view.findViewById(R.id.todo_important_btn) as CheckBox?)?.setChecked(isImportant)
    }
}
