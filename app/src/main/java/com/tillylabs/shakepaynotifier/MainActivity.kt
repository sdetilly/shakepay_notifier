package com.tillylabs.shakepaynotifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
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
import com.tillylabs.shakepaynotifier.ui.theme.ShakepayNotifierTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShakepayNotifierTheme {
                // A surface container using the 'background' color from the theme
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
fun Content() {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier.align(Alignment.Center),
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
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShakepayNotifierTheme {
        Content()
    }
}
