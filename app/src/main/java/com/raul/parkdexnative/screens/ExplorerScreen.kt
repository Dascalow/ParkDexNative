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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.raul.parkdexnative.data.ApiClient
import com.raul.parkdexnative.data.CharacterImageMapper
import com.raul.parkdexnative.data.CharacterModel
import com.raul.parkdexnative.data.SharedState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorerScreen(sharedState: SharedState) {
    var searchQuery by remember { mutableStateOf("") }
    val characters = remember { mutableStateListOf<CharacterModel>() }
    val scope = rememberCoroutineScope()

    // REPARAT AICI: Acolada a fost inchisa corect
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        scope.launch {
            isLoading = true
            try {
                // Apeleaza noua functie care aduna 100 de caractere
                val fetchedCharacters = ApiClient.getCharacters()
                characters.clear()
                if (fetchedCharacters.isNotEmpty()) {
                    characters.addAll(fetchedCharacters)
                } else {
                    throw Exception("API returned empty")
                }
            } catch (e: Exception) {
                // Daca nu ai net absolut deloc, iti lasa doar familia lui Kyle
                characters.clear()
                characters.addAll(
                    listOf(
                        CharacterModel(1, "Gerald Broflovski", "Male", "Jewish"),
                        CharacterModel(2, "Sheila Broflovski", "Female", "Jewish"),
                        CharacterModel(3, "Kyle Broflovski", "Male", "Jewish"),
                        CharacterModel(4, "Ike Broflovski", "Male", "Jewish")
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

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(width = 3.dp, color = sharedState.themeTextColor),
            placeholder = { Text("Search for a character...", color = Color.Gray, fontSize = 14.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = sharedState.themeTextColor) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = if (sharedState.appTheme == "dark") Color(0xFF2C2C2C) else Color(0XFFF5F5F5),
                unfocusedContainerColor = if (sharedState.appTheme == "dark") Color(0xFF2C2C2C) else Color(0XFFF5F5F5),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = sharedState.themeTextColor,
                unfocusedTextColor = sharedState.themeTextColor
            ),
            shape = CutCornerShape(0.dp),
            singleLine = true
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = sharedState.accentColor)
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
                            .border(width = 3.dp, color = sharedState.themeTextColor)
                            .background(sharedState.themeBackgroundColor)
                            .clickable {
                                sharedState.cheesyPoofsEaten++
                                sharedState.saveProgress()
                                sharedState.selectedCharacter = character
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .background(if (sharedState.appTheme == "dark") Color(0xFF333333) else Color(0XFFEFEFEF))
                        ) {
                            val imageRequest = ImageRequest.Builder(context)
                                .data(CharacterImageMapper.getImageUrl(character.name))
                                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                                .crossfade(true)
                                .build()

                            SubcomposeAsyncImage(
                                model = CharacterImageMapper.getImageUrl(character.name),
                                contentDescription = character.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                contentScale = androidx.compose.ui.layout.ContentScale.Fit,
                                loading = {
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                        CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp))
                                    }
                                },
                                error = {
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.Warning, contentDescription = "Error", tint = Color.Red)
                                    }
                                }
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
                                .background(cardAccentColor.copy(alpha = 0.15f))
                                .border(width = 2.dp, color = sharedState.themeTextColor)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = character.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = sharedState.themeTextColor,
                                maxLines = 1
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = character.sex ?: "Unknown",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = sharedState.themeTextColor,
                                modifier = Modifier
                                    .border(width = 1.dp, color = sharedState.themeTextColor)
                                    .background(sharedState.themeBackgroundColor)
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            )

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = character.religion ?: "Unknown",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = sharedState.themeTextColor,
                                modifier = Modifier
                                    .border(width = 1.dp, color = sharedState.themeTextColor)
                                    .background(sharedState.themeBackgroundColor)
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}