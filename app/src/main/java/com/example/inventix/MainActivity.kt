package com.example.inventix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.inventix.ui.InventixApp
import com.example.inventix.ui.AppViewModel
import com.example.inventix.ui.theme.InventixTheme

class MainActivity : ComponentActivity() {

    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InventixTheme {
                InventixApp(appViewModel = appViewModel)
            }
        }
    }
}
