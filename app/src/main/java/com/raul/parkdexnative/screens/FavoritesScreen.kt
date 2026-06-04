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
import com.raul.parkdexnative.data.SharedState

@Composable
fun FavoritesScreen(sharedState: SharedState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 3.dp, color = Color.Black)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Menu, contentDescription = null, tint = Color.Black)
            }
            Text(
                text = "PARK DEX",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1).sp
            )
            IconButton(onClick = {}) {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.Black)
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
                color = Color.Black
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your hand-picked squad of\ndegenerates.",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    lineHeight = 18.sp
                )

                Text(
                    text = "${sharedState.favoriteCharacters.size} SAVED",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                    modifier = Modifier
                        .background(Color(0xFF17A2B8), shape = CutCornerShape(10.dp))
                        .border(width = 2.dp, color = Color.Black, shape = CutCornerShape(10.dp))
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
                            .border(width = 3.dp, color = Color.Black)
                            .background(Color.White)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(Color(0XFFEFEFEF))
                        ) {
                            Text(
                                text = "[ SP IMG ]",
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.Center)
                            )

                            IconButton(
                                onClick = { sharedState.toggleFavorite(character) },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .border(width = 2.dp, color = Color.Black, shape = CutCornerShape(20.dp))
                                    .background(Color.White, shape = CutCornerShape(20.dp))
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
                                .border(width = 3.dp, color = Color.Black)
                                .background(Color.White)
                                .clickable {
                                    sharedState.selectedCharacter = character // ACEASTA ESTE LINIA NOUA
                                }
                        ) {
                            Text(
                                text = character.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}