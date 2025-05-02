package com.example.hustlehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hustlehub.navigation.AppNavHost
import com.example.hustlehub.ui.theme.HustleHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HustleHubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost()
                }
            }
        }
    }
}

//@Composable
//fun Greeting() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(32.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text = "Welcome to HustleHub!", color = Color.Black)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    HustleHubTheme {
//        Greeting()
//    }
//}


