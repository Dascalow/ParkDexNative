package com.raul.parkdexnative.navigation

import androidx.compose.runtime.*
import com.google.firebase.auth.FirebaseAuth
import com.raul.parkdexnative.data.SharedState
import com.raul.parkdexnative.screens.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val sharedState = remember { SharedState(context) }
    val auth = FirebaseAuth.getInstance()

    // Verificăm dacă utilizatorul este deja logat
    var currentScreen by remember {
        mutableStateOf(if (auth.currentUser != null) "Explorer" else "Login")
    }

    when (currentScreen) {
        "Login" -> LoginScreen(
            onLoginSuccess = { currentScreen = "Explorer" }
        )
        "Explorer" -> MainAppScaffold(
            sharedState = sharedState,
            onLogout = {
                auth.signOut()
                currentScreen = "Login"
            }
        )
    }
}