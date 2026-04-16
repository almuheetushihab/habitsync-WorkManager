package com.shihab.workmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shihab.workmanager.ui.theme.WorkmanagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WorkManagerScreen()
                }
            }
        }
    }
}

@Composable
fun WorkManagerScreen(viewModel: HabitViewModel = viewModel()) {
    // WorkManager এর স্ট্যাটাস অবজার্ভ করা হচ্ছে
    val syncWorkInfo by viewModel.syncWorkInfo.observeAsState(emptyList())
    val chainedWorkInfo by viewModel.chainedWorkInfo.observeAsState(emptyList())

    // স্ট্যাটাস বের করার লজিক
    val syncStatus = syncWorkInfo.firstOrNull()?.state?.name ?: "IDLE"
    val chainedStatus = chainedWorkInfo.lastOrNull()?.state?.name ?: "IDLE"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "WorkManager Practice", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(32.dp))

        // --- OneTime Work Section ---
        Text(text = "OneTime Sync Status: $syncStatus", color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.startOneTimeSync() }, modifier = Modifier.fillMaxWidth()) {
            Text("Start OneTime Sync")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Chained Work Section ---
        Text(text = "Chained Work Status: $chainedStatus", color = MaterialTheme.colorScheme.secondary)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.startChainedWork() }, modifier = Modifier.fillMaxWidth()) {
            Text("Start Download -> Process")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Periodic Work Section ---
        Button(onClick = { viewModel.startPeriodicReminder() }, modifier = Modifier.fillMaxWidth()) {
            Text("Start 15-Min Periodic Work")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Cancel All ---
        OutlinedButton(onClick = { viewModel.cancelAllWork() }, modifier = Modifier.fillMaxWidth()) {
            Text("Cancel All Works", color = MaterialTheme.colorScheme.error)
        }
    }
}