package com.shihab.workmanager.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class DownloadWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(
    context,
    params
) {
    override suspend fun doWork(): Result {
        Log.d("WorkManagerDemo", "Downloading data...")
        delay(2000)
        Log.d("WorkManagerDemo", "Download finished!")
        return Result.success()
    }
}