package com.tillylabs.shakepaynotifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Content() {
    val context = LocalContext.current
    val notificationPermissionState = rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    val openShakepayIntent = context.packageManager.getLaunchIntentForPackage("com.shaketh")
                    try {
                        context.startActivity(openShakepayIntent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            ) {
                Text(text = "LAUNCH SHAKEPAY")
            }
            Button(
                onClick = {
                    if (notificationPermissionState.status.isGranted) {
                        val notificationService = LocalNotificationService(context)
                        notificationService.scheduleNotifications()
                    } else {
                        notificationPermissionState.launchPermissionRequest()
                    }
                }
            ) {
                Text(text = "LAUNCH Notification")
            }
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
