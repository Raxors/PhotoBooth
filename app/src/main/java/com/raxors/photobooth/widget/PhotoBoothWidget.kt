package com.raxors.photobooth.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import com.google.gson.Gson
import com.kernel.finch.networklog.okhttp.FinchOkHttpLogger
import com.raxors.photobooth.R
import com.raxors.photobooth.data.model.response.LoginResponse
import com.raxors.photobooth.data.model.response.PhotoResponse
import com.raxors.photobooth.di.BASE_PHOTO_URL
import com.raxors.photobooth.di.BASE_URL
import com.raxors.photobooth.utils.Prefs.getParcelable
import okhttp3.*
import java.io.IOException

/**
 * Implementation of App Widget functionality.
 */
class PhotoBoothWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
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
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, R.layout.photo_booth_widget)

    appWidgetManager.updateAppWidget(appWidgetId, views) // continues after this

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

    fetchData(appWidgetManager, appWidgetId, views, context)
}

fun fetchData(
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    views: RemoteViews,
    context: Context
) {
    val serverUrl = BASE_URL + "image/last"

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val authToken: LoginResponse? = sharedPreferences.getParcelable("AUTH_TOKEN")
    val request = Request.Builder().url(serverUrl).build()
    val client = OkHttpClient.Builder()
        .addInterceptor {
            if (authToken != null) {
                val original = it.request()
                val token = "${authToken.tokenType} ${authToken.accessToken}"
                val newRequest = original.newBuilder()
                    .header("Authorization", token)
                    .method(original.method, original.body).build()
                it.proceed(newRequest)
            } else
                it.proceed(it.request())
        }
        .addInterceptor(FinchOkHttpLogger.logger as Interceptor)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            Log.i("WIDGET_TAG", "GET request successful.")

            val body = response.body.string()

            val lastPhoto: PhotoResponse = Gson().fromJson(body, PhotoResponse::class.java)
            val appWidgetTarget = AppWidgetTarget(context, R.id.iv_photo_widget, views, appWidgetId)
            Glide.with(context).asBitmap().load(BASE_PHOTO_URL + lastPhoto.path).into(appWidgetTarget)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        override fun onFailure(call: Call, e: IOException) {
            Log.i("WIDGET_TAG", "Failed to execute GET request.")
        }
    })
}