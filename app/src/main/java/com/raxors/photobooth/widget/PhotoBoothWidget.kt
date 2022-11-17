package com.raxors.photobooth.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.RemoteViews
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import com.raxors.photobooth.R
import com.raxors.photobooth.data.model.response.PhotoResponse
import com.raxors.photobooth.di.BASE_PHOTO_URL
import com.raxors.photobooth.ui.MainActivity
import com.raxors.photobooth.utils.Extensions.getPendingSelfIntent
import com.raxors.photobooth.utils.Extensions.parcelable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoBoothWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        val action = intent?.action
        if (action == WIDGET_UPDATE_CLICK) {
            context?.let {
                val work = OneTimeWorkRequest.Builder(PhotoBoothWidgetWorker::class.java).build()
                WorkManager.getInstance(it).enqueue(work)
            }
            return
        }
        val widgetIds = intent?.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)
        val lastPhoto = intent?.parcelable<PhotoResponse>(PhotoBoothWidgetWorker.EXTRA_LAST_PHOTO)
        if (context != null && widgetIds != null) {
            for (widgetId in widgetIds) {
                if (lastPhoto != null && action == WIDGET_UPDATE_PHOTO)
                    updateAppWidget(
                        context,
                        AppWidgetManager.getInstance(context),
                        widgetId,
                        lastPhoto
                    )
            }
        }
    }

    override fun onEnabled(context: Context) {
        PhotoBoothWidgetWorker.startPeriodicWork(context)
        super.onEnabled(context)
    }

    override fun onDisabled(context: Context) {
        PhotoBoothWidgetWorker.stopPeriodicWork(context)
        super.onDisabled(context)
    }

    companion object {
        const val WIDGET_UPDATE_PHOTO = "widgetUpdatePhoto"
        const val WIDGET_UPDATE_CLICK = "widgetUpdateClick"
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    photoResponse: PhotoResponse
) {
    val views = RemoteViews(context.packageName, R.layout.photo_booth_widget)

    Log.i("WIDGET_TAG", "Refreshing widget.")
    val idArray = intArrayOf(appWidgetId)
    val bundle = Bundle().apply {
        putIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray)
    }
    views.setOnClickPendingIntent(
        R.id.btn_widget_refresh,
        getPendingSelfIntent<PhotoBoothWidget>(
            context,
            PhotoBoothWidget.WIDGET_UPDATE_CLICK,
            bundle
        )
    )

    //TODO если прилка открыта то по виджету открывается новая активити, и их становится 2 :(
    val openIntent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        openIntent,
        PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    views.setOnClickPendingIntent(R.id.iv_photo_widget, pendingIntent)

    val appWidgetTarget = AppWidgetTarget(context, R.id.iv_photo_widget, views, appWidgetId)
    Glide.with(context)
        .asBitmap()
        .load(BASE_PHOTO_URL + photoResponse.path)
        .override(500, 500)
        .into(appWidgetTarget)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}