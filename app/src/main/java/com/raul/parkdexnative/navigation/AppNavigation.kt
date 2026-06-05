package com.raul.parkdexnative.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.raul.parkdexnative.screens.LoginScreen
import com.raul.parkdexnative.screens.MainTabsScreen
import com.raul.parkdexnative.screens.SignUpScreen
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.collectAsState
import com.raul.parkdexnative.ui.ThemeManager
import com.raul.parkdexnative.data.SharedState

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf("login") }
    val context = LocalContext.current
    val themeManager = remember { ThemeManager(context) }
    val savedTheme by themeManager.getTheme.collectAsState(initial = "classic")
    val sharedState = remember { SharedState(context) }

    LaunchedEffect(savedTheme) {
        sharedState.appTheme = savedTheme
    }
    when (currentScreen) {
        "login" -> {
            LoginScreen(
                onLoginSuccess = {
                    currentScreen = "main_tabs"
                },
                onNavigateToSignUp = {
                    currentScreen = "signup"
                }
            )
        }
        "signup" -> {
            SignUpScreen(
                onSignUpSuccess = {
                    // Dupa inregistrare, trimitem utilizatorul direct in aplicatie (sau la login)
                    currentScreen = "main_tabs"
                },
                onNavigateToLogin = {
                    currentScreen = "login"
                }
            )
        }
        "main_tabs" -> {
            MainTabsScreen(
                sharedState = sharedState,
                onLogout = {
                    currentScreen = "login"
                }
            )
        }
    }
}