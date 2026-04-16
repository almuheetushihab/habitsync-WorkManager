package com.shihab.workmanager.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class ProcessWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        Log.d("WorkManagerDemo", "Processing downloaded data...")
        delay(2000)
        Log.d("WorkManagerDemo", "Processing complete!")
        return Result.success()
    }
}