package com.raul.parkdexnative.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.raul.parkdexnative.screens.LoginScreen
import com.raul.parkdexnative.screens.MainTabsScreen

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf("login") }

    when (currentScreen) {
        "login" -> {
            LoginScreen(
                onLoginSuccess = {
                    currentScreen = "main_tabs"
                },
                onNavigateToSignUp = {

                }
            )
        }
        "main_tabs" -> {
            MainTabsScreen(
                onLogout = {
                    currentScreen = "login"
                }
            )
        }
    }
}