package vu.co.mikeroll.ebony.appwidget

import android.widget.RemoteViewsService
import android.content.Intent
import android.widget.RemoteViews
import android.content.Context
import android.database.Cursor
import vu.co.mikeroll.ebony.db.Database
import vu.co.mikeroll.ebony.app.R
import vu.co.mikeroll.ebony.db.Todos

public class EbonyWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?) =
        EbonyRemoteViewsFactory(this.getApplicationContext()!!, intent!!)
}

class EbonyRemoteViewsFactory(private val ctx: Context, intent: Intent)
    : RemoteViewsService.RemoteViewsFactory {

    var cursor: Cursor? = null

    override fun onCreate() {
        Database.connect(ctx)
    }

    override fun onDataSetChanged() {
        cursor = Database.selectAll()
    }

    override fun onDestroy() {
        cursor!!.close()
    }

    override fun getCount() = cursor!!.getCount()

    override fun getViewAt(position: Int): RemoteViews? {
        cursor!!.moveToPosition(position)
        val content = cursor!!.getString(cursor!!.getColumnIndex(Todos.KEY_CONTENT))
        val important = cursor!!.getInt(cursor!!.getColumnIndex(Todos.KEY_IS_IMPORTANT)) != 0

        val rv = RemoteViews(ctx.getPackageName(), R.layout.widget_todo_item)
        rv.setTextViewText(R.id.widget_todo_item_content, content)

        return rv
    }
    override fun getLoadingView() = null
    override fun getViewTypeCount() = 1
    override fun getItemId(position: Int): Long {
        cursor!!.moveToPosition(position)
        return cursor!!.getLong(cursor!!.getColumnIndex(Todos._ID))
    }
    override fun hasStableIds() = true
}