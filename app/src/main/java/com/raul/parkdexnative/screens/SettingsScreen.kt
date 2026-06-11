package com.raul.parkdexnative.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Switch
import androidx.compose.ui.unit.sp
import com.raul.parkdexnative.data.SharedState

@Composable
fun SettingsScreen(sharedState: SharedState) {
    val scrollState = rememberScrollState()

    val accentColors = listOf(
        Color(0XFFE74C3C), Color(0XFF3498DB), Color(0XFF2ECC71), Color(0XFFE67E22), Color(0xFF17A2B8)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(sharedState.themeBackgroundColor)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 3.dp, color = sharedState.themeTextColor)
                .padding(16.dp)
        ) {
            Text(
                text = "Settings",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = sharedState.themeTextColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Configure your Park Dex experience.",
                fontSize = 13.sp,
                color = if (sharedState.appTheme == "dark") Color.LightGray else Color.Gray
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            // Container Switch Theme
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 3.dp, color = sharedState.themeTextColor)
                    .background(if (sharedState.appTheme == "dark") Color(0xFF2C2C2C) else Color(0XFFF5F5F5))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Settings, contentDescription = null, tint = sharedState.themeTextColor, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Switch Theme", fontSize = 14.sp, fontWeight = FontWeight.Black, color = sharedState.themeTextColor)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    val themes = listOf("light" to "LIGHT", "dark" to "DARK", "classic" to "CLASSIC SP")

                    themes.forEach { (themeId, themeName) ->
                        val isSelected = sharedState.appTheme == themeId
                        val bgBtnColor = when (themeId) {
                            "classic" -> Color(0XFFFFE082)
                            "dark" -> Color(0XFF263238)
                            else -> Color.White
                        }
                        val textBtnColor = if (themeId == "dark") Color.White else Color.Black

                        Button(
                            onClick = {
                                sharedState.appTheme = themeId
                                sharedState.saveProgress()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(width = if (isSelected) 4.dp else 2.dp, color = sharedState.themeTextColor),
                            colors = ButtonDefaults.buttonColors(containerColor = bgBtnColor),
                            shape = CutCornerShape(0.dp),
                            contentPadding = PaddingValues(vertical = 16.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = when (themeId) {
                                        "light" -> Icons.Default.Refresh
                                        "dark" -> Icons.Default.Clear
                                        else -> Icons.Default.Settings
                                    },
                                    contentDescription = null,
                                    tint = textBtnColor,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(themeName, fontSize = 12.sp, fontWeight = FontWeight.Black, color = textBtnColor)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 3.dp, color = sharedState.themeTextColor)
                    .background(if (sharedState.appTheme == "dark") Color(0xFF2C2C2C) else Color(0XFFF5F5F5))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Settings, contentDescription = null, tint = sharedState.themeTextColor, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Change UI Accent Color", fontSize = 14.sp, fontWeight = FontWeight.Black, color = sharedState.themeTextColor)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    accentColors.forEach { colorValue ->
                        val isColorSelected = sharedState.accentColor == colorValue

                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(colorValue)
                                .border(width = if (isColorSelected) 3.dp else 1.dp, color = sharedState.themeTextColor, shape = CircleShape)
                                .clickable {
                                    sharedState.accentColor = colorValue
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (isColorSelected) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }
        }
        Text(
            text = "Character Settings",
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = sharedState.themeTextColor,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 3.dp, color = sharedState.themeTextColor)
                .background(if (sharedState.appTheme == "dark") Color(0xFF2C2C2C) else Color(0XFFF5F5F5))
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Stick of Truth Mode",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = sharedState.themeTextColor
                )
                Text(
                    text = "Call me 'Douchebag' instead of my real name",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }

            Switch(
                checked = sharedState.isDouchebagMode,
                onCheckedChange = {
                    sharedState.isDouchebagMode = it
                    sharedState.saveProgress()
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = sharedState.accentColor,
                    checkedTrackColor = sharedState.accentColor.copy(alpha = 0.5f)
                )
            )
        }
    }
}