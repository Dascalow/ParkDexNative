package com.raul.parkdexnative.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.raul.parkdexnative.data.CharacterImageMapper
import com.raul.parkdexnative.data.SharedState

@Composable
fun FavoritesScreen(sharedState: SharedState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(sharedState.themeBackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 3.dp, color = sharedState.themeTextColor)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Menu, contentDescription = null, tint = sharedState.themeTextColor)
            }
            Text(
                text = "PARK DEX",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1).sp,
                color = sharedState.themeTextColor
            )
            IconButton(onClick = {}) {
                Icon(Icons.Default.Search, contentDescription = null, tint = sharedState.themeTextColor)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "My Roster",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = sharedState.themeTextColor
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your hand-picked squad of\ndegenerates.",
                    fontSize = 13.sp,
                    color = if (sharedState.appTheme == "dark") Color.LightGray else Color.Gray,
                    lineHeight = 18.sp
                )

                Text(
                    text = "${sharedState.favoriteCharacters.size} SAVED",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    modifier = Modifier
                        .background(sharedState.accentColor, shape = CutCornerShape(10.dp))
                        .border(width = 2.dp, color = sharedState.themeTextColor, shape = CutCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        if (sharedState.favoriteCharacters.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No characters saved yet.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(sharedState.favoriteCharacters.size) { index ->
                    val character = sharedState.favoriteCharacters[index]

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 3.dp, color = sharedState.themeTextColor)
                            .background(sharedState.themeBackgroundColor)
                            .clickable {
                                sharedState.selectedCharacter = character
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(if (sharedState.appTheme == "dark") Color(0xFF333333) else Color(0XFFEFEFEF))
                        ) {

                            AsyncImage(
                                model = CharacterImageMapper.getImageUrl(character.name), // Schimbat din .id în .name
                                contentDescription = character.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentScale = androidx.compose.ui.layout.ContentScale.Fit
                            )

                            IconButton(
                                onClick = { sharedState.toggleFavorite(character) },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .border(width = 2.dp, color = sharedState.themeTextColor, shape = CutCornerShape(20.dp))
                                    .background(sharedState.themeBackgroundColor, shape = CutCornerShape(20.dp))
                                    .size(36.dp)
                            ) {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = Color.Red,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(width = 2.dp, color = sharedState.themeTextColor)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = character.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = sharedState.themeTextColor
                            )
                        }
                    }
                }
            }
        }
    }
}