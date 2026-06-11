package com.raul.parkdexnative.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.raul.parkdexnative.data.CharacterBioMapper
import com.raul.parkdexnative.data.CharacterImageMapper
import com.raul.parkdexnative.data.CharacterModel
import com.raul.parkdexnative.data.SharedState

@Composable
fun ProfileScreen(character: CharacterModel, sharedState: SharedState, onBackClick: () -> Unit) {
    val scrollState = rememberScrollState()
    val isFav = sharedState.isFavorite(character)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(sharedState.themeBackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 3.dp, color = sharedState.themeTextColor)
                .background(sharedState.themeBackgroundColor)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = null, tint = sharedState.themeTextColor)
            }
            Text(
                text = "PROFILE",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = sharedState.themeTextColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color(0xFFE67E22))
                    .border(width = 3.dp, color = sharedState.themeTextColor)
            ) {
                AsyncImage(
                    model = CharacterImageMapper.getImageUrl(character.name),
                    contentDescription = character.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Fit
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-20).dp)
                    .padding(horizontal = 12.dp)
                    .border(width = 3.dp, color = sharedState.themeTextColor)
                    .background(sharedState.themeBackgroundColor)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = character.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = sharedState.themeTextColor
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "South Park Resident",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = sharedState.themeTextColor,
                        modifier = Modifier
                            .border(width = 1.dp, color = sharedState.themeTextColor)
                            .background(if (sharedState.appTheme == "dark") Color(0xFF2C2C2C) else Color(0XFFF5F5F5))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                IconButton(
                    onClick = { sharedState.toggleFavorite(character) },
                    modifier = Modifier
                        .border(width = 2.dp, color = sharedState.themeTextColor, shape = CutCornerShape(20.dp))
                        .background(if (isFav) Color(0XFFFFCDD2) else (if (sharedState.appTheme == "dark") Color(0xFF333333) else Color(0XFFEFEFEF)), shape = CutCornerShape(20.dp))
                        .size(40.dp)
                ) {
                    Icon(
                        if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFav) Color(0XFFE74C3C) else Color.Gray
                    )
                }
            }

            Text(
                text = "Quick Stats",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = sharedState.themeTextColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(modifier = Modifier.weight(1f).border(width = 2.dp, color = sharedState.themeTextColor).background(if (sharedState.appTheme == "dark") Color(0xFF2C2C2C) else Color(0XFFF9F9F9)).padding(8.dp)) {
                    Text("Sex", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Text(character.sex ?: "Unknown", fontSize = 14.sp, fontWeight = FontWeight.Black, color = sharedState.themeTextColor)
                }
                Column(modifier = Modifier.weight(1f).border(width = 2.dp, color = sharedState.themeTextColor).background(if (sharedState.appTheme == "dark") Color(0xFF2C2C2C) else Color(0XFFF9F9F9)).padding(8.dp)) {
                    Text("Religion", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Text(character.religion ?: "Unknown", fontSize = 14.sp, fontWeight = FontWeight.Black, color = sharedState.themeTextColor)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 3.dp, color = sharedState.themeTextColor)
                    .background(Color(0xFF005A5B))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Bio", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = CharacterBioMapper.getBio(character.name),
                    fontSize = 13.sp,
                    color = Color.White,
                    lineHeight = 18.sp
                )
            }
        }
    }
}