package com.shihab.workmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.shihab.workmanager.workers.DownloadWorker
import com.shihab.workmanager.workers.ProcessWorker
import com.shihab.workmanager.workers.SyncWorker
import java.util.concurrent.TimeUnit

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val workManager = WorkManager.getInstance(application)

    // UI তে স্ট্যাটাস দেখানোর জন্য
    internal val syncWorkInfo: LiveData<List<WorkInfo>> = workManager.getWorkInfosByTagLiveData("sync_tag")
    internal val chainedWorkInfo: LiveData<List<WorkInfo>> = workManager.getWorkInfosByTagLiveData("chained_tag")

    // ১. OneTime Work (একবারের কাজ)
    fun startOneTimeSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // শুধু ইন্টারনেট থাকলেই চলবে
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .addTag("sync_tag")
            .build()

        workManager.enqueueUniqueWork(
            "one_time_sync",
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }

    // ২. Work Chaining (একের পর এক কাজ)
    fun startChainedWork() {
        val downloadRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .addTag("chained_tag")
            .build()

        val processRequest = OneTimeWorkRequestBuilder<ProcessWorker>()
            .addTag("chained_tag")
            .build()

        // প্রথমে ডাউনলোড, তারপর প্রসেস
        workManager.beginWith(downloadRequest)
            .then(processRequest)
            .enqueue()
    }

    // ৩. Periodic Work (নির্দিষ্ট সময় পরপর)
    fun startPeriodicReminder() {
        val reminderRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES) // সর্বনিম্ন ১৫ মিনিট
            .addTag("periodic_tag")
            .build()

        workManager.enqueueUniquePeriodicWork(
            "daily_reminder",
            ExistingPeriodicWorkPolicy.KEEP,
            reminderRequest
        )
    }

    // কাজ ক্যানসেল করার জন্য
    fun cancelAllWork() {
        workManager.cancelAllWork()
    }
}