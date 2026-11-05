package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ui.MalwareScreen
import com.example.ui.MalwareViewModel
import com.example.ui.theme.MalwareRemovalTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MalwareViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MalwareRemovalTheme {
                MalwareScreen(viewModel = viewModel)
            }
        }
    }
}
