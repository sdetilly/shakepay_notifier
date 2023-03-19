package com.tillylabs.shakepaynotifier

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.tillylabs.shakepaynotifier.ui.theme.ShakepayNotifierTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShakepayNotifierTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }
}

@Composable
private fun Content() {
    val context = LocalContext.current
    RequestPermissionIfNeeded(notificationService = LocalNotificationService(context))
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = context.getString(R.string.time_until_next_alarm, TimeUtils.formattedTimeUntilOneAm()))
        }
    }
}

@Composable
private fun RequestPermissionIfNeeded(notificationService: LocalNotificationService) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        CheckAndRequestPermission(notificationService)
    } else {
        notificationService.scheduleNotifications()
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CheckAndRequestPermission(notificationService: LocalNotificationService) {
    val notificationPermissionState = rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)
    LaunchedEffect(key1 = Unit) {
        if (notificationPermissionState.status.isGranted) {
            notificationService.scheduleNotifications()
        } else {
            notificationPermissionState.launchPermissionRequest()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShakepayNotifierTheme {
        Content()
    }
}
