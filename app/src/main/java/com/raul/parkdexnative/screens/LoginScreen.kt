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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onNavigateToSignUp: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var secretCode by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
    ) {
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
                fontSize = 36.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1).sp,
                color = Color.Black
            )

            Text(
                text = "UNAUTHORIZED ENCYCLOPEDIA",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .background(Color.Black)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .border(width = 3.dp, color = Color.Black),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF17A2B8)),
                    shape = CutCornerShape(0.dp)
                ) {
                    Text("LOGIN", fontWeight = FontWeight.Black, color = Color.Black)
                }

                Button(
                    onClick = onNavigateToSignUp,
                    modifier = Modifier
                        .weight(1f)
                        .border(width = 3.dp, color = Color.Black),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = CutCornerShape(0.dp)
                ) {
                    Text("SIGN UP", fontWeight = FontWeight.Black, color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "USERNAME / ALIAS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .border(width = 2.dp, color = Color.Black),
                placeholder = { Text("e.g. Coon, Mysterion", color = Color.Gray, fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.Black) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = CutCornerShape(0.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "SECRET CODE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            OutlinedTextField(
                value = secretCode,
                onValueChange = { secretCode = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .border(width = 2.dp, color = Color.Black),
                placeholder = { Text("********", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Black) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = CutCornerShape(0.dp),
                singleLine = true
            )

            Text(
                text = "Forgot it, dude?",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.End,
                color = Color(0xFF17A2B8)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onLoginSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 3.dp, color = Color.Black),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC0392B)),
                shape = CutCornerShape(0.dp),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text(
                    text = "ENTER SOUTH PARK ->",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }
        }
    }
}