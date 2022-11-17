package com.raxors.photobooth.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.raxors.photobooth.data.repository.image.PhotoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class PhotoBoothWidgetWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val photoRepo: PhotoRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val lastImage = photoRepo.getLastImage()
        val action = PhotoBoothWidget.WIDGET_UPDATE_PHOTO
        val intent = Intent(
            action, null, context,
            PhotoBoothWidget::class.java
        )
        val ids = AppWidgetManager.getInstance(context)
            .getAppWidgetIds(ComponentName(context, PhotoBoothWidget::class.java))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        intent.putExtra(EXTRA_LAST_PHOTO, lastImage)
        context.sendBroadcast(intent)
        return Result.success()
    }

    companion object {
        const val EXTRA_LAST_PHOTO = "extraLastPhoto"

        private const val WORK_NAME = "PRODUCT_APP_WIDGET_UPDATE_WORK"

        @JvmStatic
        fun startPeriodicWork(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest =
                PeriodicWorkRequestBuilder<PhotoBoothWidgetWorker>(30, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest
            )
        }

        fun stopPeriodicWork(context: Context) =
            WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }

}