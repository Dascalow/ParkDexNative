package com.raul.parkdexnative.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(onSignUpSuccess: () -> Unit, onNavigateToLogin: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var secretCode by remember { mutableStateOf("") }
    var confirmSecretCode by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val auth = remember { FirebaseAuth.getInstance() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
    ) {
        // Brazii identici de pe fundal din Login
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val treeColor = Color(0xFF005A5B)

            val leftTree = Path().apply {
                moveTo(width * 0.05f, height)
                lineTo(width * 0.25f, height * 0.75f)
                lineTo(width * 0.45f, height)
                close()
            }
            drawPath(path = leftTree, color = treeColor)

            val rightTree = Path().apply {
                moveTo(width * 0.55f, height)
                lineTo(width * 0.75f, height * 0.75f)
                lineTo(width * 0.95f, height)
                close()
            }
            drawPath(path = rightTree, color = treeColor)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 24.dp)
                .border(width = 4.dp, color = Color.Black)
                .background(Color.White)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "PARK DEX",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1).sp,
                color = Color.Black
            )

            Text(
                text = "CREATE NEW PROFILE",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .background(Color.Black)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Butoanele de sus inversate ca sa arate ca esti pe Sign Up
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onNavigateToLogin,
                    modifier = Modifier
                        .weight(1f)
                        .border(width = 3.dp, color = Color.Black),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = CutCornerShape(0.dp)
                ) {
                    Text("LOGIN", fontWeight = FontWeight.Black, color = Color.Black)
                }

                Button(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .border(width = 3.dp, color = Color.Black),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2ECC71)), // Verde activ pentru Sign Up
                    shape = CutCornerShape(0.dp)
                ) {
                    Text("SIGN UP", fontWeight = FontWeight.Black, color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Campul de Username
            Text(
                text = "CHOOSE USERNAME / ALIAS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it; errorMessage = null },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .border(width = 2.dp, color = Color.Black),
                placeholder = { Text("e.g. Coon, Mysterion", color = Color.Gray, fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.Black) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black, unfocusedTextColor = Color.Black
                ),
                shape = CutCornerShape(0.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campul Secret Code (Parola)
            Text(
                text = "SECRET CODE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            OutlinedTextField(
                value = secretCode,
                onValueChange = { secretCode = it; errorMessage = null },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .border(width = 2.dp, color = Color.Black),
                placeholder = { Text("********", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Black) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black, unfocusedTextColor = Color.Black
                ),
                shape = CutCornerShape(0.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campul de Confirmare Parola
            Text(
                text = "CONFIRM SECRET CODE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            OutlinedTextField(
                value = confirmSecretCode,
                onValueChange = { confirmSecretCode = it; errorMessage = null },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .border(width = 2.dp, color = Color.Black),
                placeholder = { Text("********", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Black) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black, unfocusedTextColor = Color.Black
                ),
                shape = CutCornerShape(0.dp),
                singleLine = true
            )

            // Afisare erori stilizate
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Red, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = errorMessage!!, color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Butonul principal de trimitere
            Button(
                onClick = {
                    val cleanUsername = username.trim().lowercase().replace(" ", "")

                    if (cleanUsername.isBlank() || secretCode.isBlank() || confirmSecretCode.isBlank()) {
                        errorMessage = "All fields are required, dude."
                    } else if (secretCode.length < 6) {
                        errorMessage = "Secret code must be at least 6 characters."
                    } else if (secretCode != confirmSecretCode) {
                        errorMessage = "Secret codes do not match."
                    } else {
                        val fakeEmail = "$cleanUsername@parkdex.com"

                        isLoading = true
                        // Apelam Firebase ca sa creeze contul
                        auth.createUserWithEmailAndPassword(fakeEmail, secretCode)
                            .addOnCompleteListener { task ->
                                isLoading = false
                                if (task.isSuccessful) {
                                    onSignUpSuccess() // Cont creat cu succes! Mergem in aplicatie
                                } else {
                                    // Daca ceva pica (ex: username deja luat), afisam eroarea de la server
                                    errorMessage = task.exception?.localizedMessage ?: "Registration failed."
                                }
                            }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 3.dp, color = Color.Black),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC0392B)),
                shape = CutCornerShape(0.dp),
                contentPadding = PaddingValues(vertical = 14.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 3.dp)
                } else {
                    Text(
                        text = "REGISTER KID ->",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
            }
        }
    }
}