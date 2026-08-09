package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.navigation.InstaNavGraph
import com.example.ui.theme.InstaTheme
import com.example.ui.viewmodel.InstaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: InstaViewModel = viewModel()
            val isDarkMode by viewModel.isDarkMode.collectAsState()

            InstaTheme(darkTheme = isDarkMode) {
                InstaNavGraph(viewModel = viewModel)
            }
        }
    }
}
