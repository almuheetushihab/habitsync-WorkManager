package com.shihab.workmanager.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            Log.d("WorkManagerDemo", "Syncing started...")
            // ডেমো হিসেবে ৩ সেকেন্ড ওয়েট করানো হচ্ছে (এখানে API কল হতে পারে)
            delay(3000)
            Log.d("WorkManagerDemo", "Syncing completed successfully!")
            Result.success()
        } catch (e: Exception) {
            Log.e("WorkManagerDemo", "Syncing failed!", e)
            Result.failure()
        }
    }
}