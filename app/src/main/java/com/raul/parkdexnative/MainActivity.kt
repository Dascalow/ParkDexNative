package com.raul.parkdexnative

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.raul.parkdexnative.screens.LoginScreen
import com.raul.parkdexnative.ui.theme.ParkDexNativeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParkDexNativeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onLoginSuccess = {
                            Toast.makeText(this@MainActivity, "Respect my authoritah!", Toast.LENGTH_SHORT).show()
                        },
                        onNavigateToSignUp = {
                            Toast.makeText(this@MainActivity, "Sign Up Screen", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}