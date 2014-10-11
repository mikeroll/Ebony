package vu.co.mikeroll.ebony.app

import android.appwidget.AppWidgetProvider
import android.content.Context
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.app.PendingIntent
import android.widget.RemoteViews

public class EbonyWidgetProvider() : AppWidgetProvider() {

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        for (appWidgetId in appWidgetIds!!) {
            val intent = Intent(context!!, javaClass<EbonyMain>())
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val views = RemoteViews(context.getPackageName(), R.layout.widget_todo_list)
            views.setOnClickPendingIntent(R.id.btn, pendingIntent)

            appWidgetManager?.updateAppWidget(appWidgetId, views)
        }
    }
}