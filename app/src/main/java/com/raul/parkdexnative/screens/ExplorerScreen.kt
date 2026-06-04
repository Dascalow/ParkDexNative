package com.raul.parkdex.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CharacterPreview(
    val name: String,
    val role: String,
    val group: String,
    val headerColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorerScreen() {
    var searchQuery by remember { mutableStateOf("") }

    val mockCharacters = listOf(
        CharacterPreview("Cartman", "Main Character", "Student", Color(0XFFE74C3C)),
        CharacterPreview("Stan", "Main Character", "Student", Color(0XFF3498DB)),
        CharacterPreview("Kyle", "Main Character", "Student", Color(0XFF2ECC71)),
        CharacterPreview("Kenny", "Main Character", "Student", Color(0XFFE67E22)),
        CharacterPreview("Butters", "Supporting", "Student", Color(0XFF9B59B6)),
        CharacterPreview("Randy", "Adult", "Geologist", Color(0XFFBDC3C7))
    )

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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(mockCharacters.size) { index ->
                val character = mockCharacters[index]

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 3.dp, color = Color.Black)
                        .background(Color.White)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .background(Color(0XFFEFEFEF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("[ IMG ]", color = Color.Gray, fontWeight = FontWeight.Bold)
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(character.headerColor.copy(alpha = 0.2f))
                            .border(width = 2.dp, color = Color.Black)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = character.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = character.role,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier
                                .border(width = 1.dp, color = Color.Black)
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = character.group,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier
                                .border(width = 1.dp, color = Color.Black)
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }
                }
            }
        }
    }
}