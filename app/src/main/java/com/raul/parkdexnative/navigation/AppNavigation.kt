package com.raul.parkdexnative.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.raul.parkdexnative.data.SharedState
import com.raul.parkdexnative.screens.LoginScreen
import com.raul.parkdexnative.screens.SignUpScreen
import com.raul.parkdexnative.screens.MainTabsScreen

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val sharedState = remember { SharedState(context) }
    val auth = FirebaseAuth.getInstance()

    var currentScreen by remember {
        mutableStateOf(if (auth.currentUser != null) "Explorer" else "Login")
    }
    when (currentScreen) {
        "Login" -> LoginScreen(
            onLoginSuccess = {
                currentScreen = "Explorer"
                sharedState.loadAllData()
                             },
            onNavigateToSignUp = { currentScreen = "SignUp" }
        )
        "SignUp" -> SignUpScreen(
            onSignUpSuccess = { currentScreen = "Explorer" },
            onNavigateToLogin = { currentScreen = "Login" }
        )

        "Explorer" -> MainTabsScreen(
            sharedState = sharedState,
            onLogout = {
                auth.signOut()
                currentScreen = "Login"
            }
        )
    }
}