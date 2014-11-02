package vu.co.mikeroll.ebony.appwidget

import android.appwidget.AppWidgetProvider
import android.content.Context
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.widget.RemoteViews
import vu.co.mikeroll.ebony.app.R
import android.net.Uri
import vu.co.mikeroll.ebony.app.EbonyMain
import android.app.PendingIntent

public class EbonyWidgetProvider() : AppWidgetProvider() {
    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        for (appWidgetId in appWidgetIds!!) {
            val intent = Intent(context!!, javaClass<EbonyWidgetService>())
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)))
            val rvs = RemoteViews(context.getPackageName(), R.layout.widget_todo_list)
            rvs.setRemoteAdapter(R.id.widget_todo_list, intent)
            appWidgetManager?.updateAppWidget(appWidgetId, rvs)

            val ebonyIntent = Intent(context, javaClass<EbonyMain>())
            val pendingEbonyIntent = PendingIntent.getActivity(context, 0, intent, 0)
            rvs.setOnClickPendingIntent(R.id.widget, pendingEbonyIntent)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }
}