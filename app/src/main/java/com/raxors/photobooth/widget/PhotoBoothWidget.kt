package com.raxors.photobooth.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import com.raxors.photobooth.R
import com.raxors.photobooth.data.model.response.PhotoResponse
import com.raxors.photobooth.data.repository.image.PhotoRepository
import com.raxors.photobooth.di.BASE_PHOTO_URL
import com.raxors.photobooth.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class PhotoBoothWidget : AppWidgetProvider() {

    @Inject
    lateinit var photoRepo: PhotoRepository

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        var photoResponse: PhotoResponse? = null
        runBlocking {
            try {
                photoResponse = photoRepo.getLastImage()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        photoResponse?.let {
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId, it)
            }
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    photoResponse: PhotoResponse
) {
    val views = RemoteViews(context.packageName, R.layout.photo_booth_widget)

//    appWidgetManager.updateAppWidget(appWidgetId, views) // continues after this

    val intentUpdate = Intent(context, PhotoBoothWidget::class.java)
    intentUpdate.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

    val idArray = intArrayOf(appWidgetId)
    intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray)

    val pendingUpdate = PendingIntent.getBroadcast(
        context,
        appWidgetId,
        intentUpdate,
        PendingIntent.FLAG_IMMUTABLE
    )
    Log.i("WIDGET_TAG", "Refreshing widget.")
    views.setOnClickPendingIntent(R.id.btn_widget_refresh, pendingUpdate)

    //TODO если прилка открыта то по виджету открывается новая активити, и их становится 2 :(
    val openIntent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, 0, openIntent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    views.setOnClickPendingIntent(R.id.iv_photo_widget, pendingIntent)

    val appWidgetTarget = AppWidgetTarget(context, R.id.iv_photo_widget, views, appWidgetId)
    Glide.with(context)
        .asBitmap()
        .load(BASE_PHOTO_URL + photoResponse.path)
        .override(500, 500)
        .into(appWidgetTarget)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}