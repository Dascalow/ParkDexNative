package com.raul.parkdexnative.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raul.parkdexnative.data.SharedState

@Composable
fun AccountScreen(
    sharedState: SharedState,
    onLogoutClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(sharedState.themeBackgroundColor)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 3.dp, color = sharedState.themeTextColor)
                .background(sharedState.accentColor)
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(Color(0XFFEFEFEF))
                    .border(width = 3.dp, color = Color.Black, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("AVATAR", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "New Kid",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Black, modifier = Modifier.size(12.dp))
                    Text("South Park, CO", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .border(width = 3.dp, color = sharedState.themeTextColor)
                        .padding(12.dp)
                ) {
                    Text("Characters Collected", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("${sharedState.favoriteCharacters.size} / 150", fontSize = 20.sp, fontWeight = FontWeight.Black, color = sharedState.themeTextColor)
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .border(width = 3.dp, color = sharedState.themeTextColor)
                        .padding(12.dp)
                ) {
                    Text("Episodes Seen", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = {
                                if (sharedState.episodesSeen > 0) {
                                    sharedState.episodesSeen--
                                    sharedState.saveProgress()
                                }
                            },
                            modifier = Modifier
                                .size(24.dp)
                                .background(if (sharedState.appTheme == "dark") Color(0xFF333333) else Color(0XFFEFEFEF))
                                .border(1.dp, sharedState.themeTextColor)
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = null, tint = sharedState.themeTextColor, modifier = Modifier.size(16.dp))
                        }
                        Text(
                            text = "${sharedState.episodesSeen}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = sharedState.themeTextColor,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        IconButton(
                            onClick = {
                                sharedState.episodesSeen++
                                sharedState.saveProgress()
                            },
                            modifier = Modifier
                                .size(24.dp)
                                .background(if (sharedState.appTheme == "dark") Color(0xFF333333) else Color(0XFFEFEFEF))
                                .border(1.dp, sharedState.themeTextColor)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = sharedState.themeTextColor, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 3.dp, color = sharedState.themeTextColor)
                    .background(Color(0XFFFFCDD2))
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Cheesy Poofs Eaten", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text("${sharedState.cheesyPoofsEaten}", fontSize = 20.sp, fontWeight = FontWeight.Black, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Achievements",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = sharedState.themeTextColor,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .border(width = 3.dp, color = sharedState.themeTextColor)
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier
                        .size(40.dp)
                        .background(if (sharedState.cheesyPoofsEaten >= 1) Color(0XFF2ECC71) else Color(0XFFF5F5F5))
                        .border(width = 1.dp, color = Color.Black))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Grounded!", fontSize = 12.sp, fontWeight = FontWeight.Black, color = sharedState.themeTextColor)
                    Text("View a character in Explorer.", fontSize = 10.sp, color = Color.Gray, textAlign = TextAlign.Center)
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .border(width = 3.dp, color = sharedState.themeTextColor)
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier
                        .size(40.dp)
                        .background(if (sharedState.favoriteCharacters.size >= 4) Color(0XFF3498DB) else Color(0XFFF5F5F5))
                        .border(width = 1.dp, color = Color.Black))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Respect My Authoritah", fontSize = 12.sp, fontWeight = FontWeight.Black, color = sharedState.themeTextColor, textAlign = TextAlign.Center)
                    Text("Save 4 characters to Favs.", fontSize = 10.sp, color = Color.Gray, textAlign = TextAlign.Center)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onLogoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 3.dp, color = sharedState.themeTextColor),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0XFFC0392B)),
                shape = CutCornerShape(0.dp),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("LOG OUT", fontSize = 15.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
        }
    }
}