package com.raul.parkdexnative.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import com.raul.parkdexnative.data.ApiClient
import com.raul.parkdexnative.data.CharacterModel
import com.raul.parkdexnative.data.SharedState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorerScreen(sharedState: SharedState) {
    var searchQuery by remember { mutableStateOf("") }
    val characters = remember { mutableStateListOf<CharacterModel>() }
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val fetchedCharacters = ApiClient.getCharacters()
                characters.clear()
                if (fetchedCharacters.isNotEmpty()) {
                    characters.addAll(fetchedCharacters)
                } else {
                    throw Exception("API returned empty")
                }
            } catch (e: Exception) {
                characters.clear()
                characters.addAll(
                    listOf(
                        CharacterModel(1, "Eric Cartman", "Male", "Catholic"),
                        CharacterModel(2, "Stan Marsh", "Male", "Catholic"),
                        CharacterModel(3, "Kyle Broflovski", "Male", "Jewish"),
                        CharacterModel(4, "Kenny McCormick", "Male", "Catholic"),
                        CharacterModel(5, "Butters Stotch", "Male", "Catholic"),
                        CharacterModel(6, "Randy Marsh", "Male", "Catholic")
                    )
                )
            } finally {
                isLoading = false
            }
        }
    }

    val filteredCharacters = characters.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

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

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(width = 3.dp, color = Color.Black),
            placeholder = { Text("Search for a character...", color = Color.Gray, fontSize = 14.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Black) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0XFFF5F5F5),
                unfocusedContainerColor = Color(0XFFF5F5F5),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = CutCornerShape(0.dp),
            singleLine = true
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF17A2B8))
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredCharacters.size) { index ->
                    val character = filteredCharacters[index]
                    val cardAccentColor = when (index % 4) {
                        0 -> Color(0XFFE74C3C)
                        1 -> Color(0XFF3498DB)
                        2 -> Color(0XFF2ECC71)
                        else -> Color(0XFFE67E22)
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 3.dp, color = Color.Black)
                            .background(Color.White)
                            .clickable {
                                sharedState.cheesyPoofsEaten++
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .background(Color(0XFFEFEFEF))
                        ) {
                            Text(
                                "[ SP IMG ]",
                                color = Color.Gray,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.Center)
                            )

                            IconButton(
                                onClick = { sharedState.toggleFavorite(character) },
                                modifier = Modifier.align(Alignment.TopEnd)
                            ) {
                                Icon(
                                    if (sharedState.isFavorite(character)) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = null,
                                    tint = if (sharedState.isFavorite(character)) Color.Red else Color.Gray
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(width = 3.dp, color = Color.Black)
                                .background(Color.White)
                                .clickable {
                                    sharedState.cheesyPoofsEaten++
                                    sharedState.selectedCharacter = character // ACEASTA ESTE LINIA NOUA
                                }
                            ) {
                            Text(
                                text = character.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black,
                                maxLines = 1
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = character.sex ?: "Unknown",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier
                                    .border(width = 1.dp, color = Color.Black)
                                    .background(Color.White)
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            )

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = character.religion ?: "Unknown",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier
                                    .border(width = 1.dp, color = Color.Black)
                                    .background(Color.White)
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}