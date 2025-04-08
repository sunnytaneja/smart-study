package com.android.smartstudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.android.smartstudy.ui.presentation.dashboard.DashboardScreen
import com.android.smartstudy.ui.theme.SmartStudyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartStudyTheme {
                DashboardScreen()
            }
        }
    }
}