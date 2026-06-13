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
import com.raul.parkdexnative.data.LoreStats
import com.raul.parkdexnative.data.SharedState

@Composable
fun ProfileScreen(character: CharacterModel, sharedState: SharedState, onBackClick: () -> Unit) {
    val scrollState = rememberScrollState()
    val isFav = sharedState.isFavorite(character)

    val loreEntry = CharacterBioMapper.getLore(character.name)

    val tags = loreEntry?.tags?.takeIf { it.isNotEmpty() } ?: listOf(character.sex ?: "UNKNOWN", "RESIDENT")
    val shortDesc = loreEntry?.shortDesc ?: "A proud citizen of South Park, Colorado. Frequently spotted around town."
    val bio = loreEntry?.bio ?: "Data from SPAPI does not include a full biography for this character yet."
    val alias = loreEntry?.alias?.takeIf { it.isNotEmpty() } ?: character.religion ?: "None"
    val age = loreEntry?.age?.takeIf { it.isNotEmpty() } ?: "Unknown"
    val grade = loreEntry?.grade?.takeIf { it.isNotEmpty() } ?: "Unknown"
    val voicedBy = loreEntry?.voicedBy?.takeIf { it.isNotEmpty() } ?: "Matt & Trey"
    val quotes = loreEntry?.quotes ?: emptyList()

    val stats = loreEntry?.stats ?: LoreStats(
        Cursing = (character.id * 7) % 85 + 10,
        Manipulation = (character.id * 13) % 75 + 5,
        Empathy = (character.id * 3) % 95 + 5,
        Athleticism = (character.id * 19) % 80 + 10
    )

    val isDark = sharedState.appTheme == "dark"
    val boxBg = if (isDark) Color(0xFF333333) else Color(0xFFF9F9F9)
    val altBoxBg = if (isDark) Color(0xFF444444) else Color(0xFFE0E0E0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(sharedState.themeBackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 3.dp, color = sharedState.themeTextColor)
                .background(if (isDark) Color(0xFF111111) else Color(0xFFF1C40F))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = null, tint = sharedState.themeTextColor)
            }
            Text(
                text = "BACK TO DEX",
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                color = sharedState.themeTextColor,
                letterSpacing = 1.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(if (isDark) Color(0xFF2C3E50) else Color(0xFF6EC3B9))
                        .border(width = 3.dp, color = sharedState.themeTextColor),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AsyncImage(
                        model = CharacterImageMapper.getImageUrl(character.id, character.name),
                        contentDescription = character.name,
                        modifier = Modifier
                            .fillMaxSize(0.9f)
                            .padding(8.dp),
                        contentScale = androidx.compose.ui.layout.ContentScale.Fit
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(bottom = 8.dp)) {
                        tags.take(2).forEachIndexed { index, tag ->
                            Text(
                                text = tag.uppercase(),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = if (index == 1) Color.White else sharedState.themeTextColor,
                                modifier = Modifier
                                    .border(2.dp, sharedState.themeTextColor)
                                    .background(if (index == 1) Color(0xFFC0392B) else boxBg)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = character.name.uppercase(),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = sharedState.themeTextColor,
                        lineHeight = 28.sp,
                        letterSpacing = (-1).sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(text = shortDesc, fontSize = 12.sp, color = if (isDark) Color.LightGray else Color.DarkGray, lineHeight = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = sharedState.themeTextColor, thickness = 3.dp)
            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = { sharedState.toggleFavorite(character) },
                    modifier = Modifier
                        .border(width = 2.dp, color = sharedState.themeTextColor, shape = CutCornerShape(20.dp))
                        .background(if (isFav) Color(0XFFFFCDD2) else altBoxBg, shape = CutCornerShape(20.dp))
                        .size(40.dp)
                ) {
                    Icon(
                        if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFav) Color(0XFFE74C3C) else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 3.dp, color = sharedState.themeTextColor)
                    .background(Color(0xFF17A2B8))
                    .padding(16.dp)
            ) {
                Text("BIOGRAPHY", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color.Black, modifier = Modifier.padding(bottom = 8.dp))
                Text(text = bio, fontSize = 13.sp, color = Color.Black, lineHeight = 18.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Column(modifier = Modifier.weight(1f).border(3.dp, sharedState.themeTextColor).background(altBoxBg).padding(8.dp)) {
                    Text("AGE", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if (isDark) Color.LightGray else Color.DarkGray)
                    Text(age, fontSize = 14.sp, fontWeight = FontWeight.Black, color = sharedState.themeTextColor)
                }
                Column(modifier = Modifier.weight(1f).border(3.dp, sharedState.themeTextColor).background(if(isDark) Color(0xFF2C3E50) else Color(0xFF6296A6)).padding(8.dp)) {
                    Text("GRADE", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if(isDark) Color.LightGray else Color.Black)
                    Text(grade, fontSize = 14.sp, fontWeight = FontWeight.Black, color = if(isDark) Color.White else Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Column(modifier = Modifier.weight(1f).border(3.dp, sharedState.themeTextColor).background(altBoxBg).padding(8.dp)) {
                    Text("ALIAS / RELIGION", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if (isDark) Color.LightGray else Color.DarkGray)
                    Text(alias, fontSize = 14.sp, fontWeight = FontWeight.Black, color = sharedState.themeTextColor, maxLines = 1)
                }
                Column(modifier = Modifier.weight(1f).border(3.dp, sharedState.themeTextColor).background(altBoxBg).padding(8.dp)) {
                    Text("VOICED BY", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if (isDark) Color.LightGray else Color.DarkGray)
                    Text(voicedBy, fontSize = 14.sp, fontWeight = FontWeight.Black, color = sharedState.themeTextColor, maxLines = 1)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 3.dp, color = sharedState.themeTextColor)
                    .background(boxBg)
                    .padding(16.dp)
            ) {
                Text("ATTRIBUTES", fontSize = 16.sp, fontWeight = FontWeight.Black, color = sharedState.themeTextColor, modifier = Modifier.padding(bottom = 16.dp))

                AttributeBar("CURSING", stats.Cursing, sharedState, altBoxBg)
                AttributeBar("MANIPULATION", stats.Manipulation, sharedState, altBoxBg)
                AttributeBar("EMPATHY", stats.Empathy, sharedState, altBoxBg)
                AttributeBar("ATHLETICISM", stats.Athleticism, sharedState, altBoxBg)
            }

            if (quotes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))
                Text("MEMORABLE QUOTES", fontSize = 16.sp, fontWeight = FontWeight.Black, color = sharedState.themeTextColor, modifier = Modifier.padding(bottom = 12.dp))

                quotes.forEach { quote ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .border(width = 3.dp, color = sharedState.themeTextColor)
                            .background(boxBg)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("❝", fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color(0xFFC0392B), modifier = Modifier.padding(end = 8.dp))
                        Text(text = quote, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = sharedState.themeTextColor, modifier = Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun AttributeBar(label: String, value: Int, sharedState: SharedState, bgBoxColor: Color) {
    val barColor = when {
        value > 80 -> Color(0xFF17A2B8)
        value < 25 -> if (sharedState.appTheme == "dark") Color(0xFF666666) else Color(0xFF34495E) // Gri inchis (Slab)
        else -> Color(0xFFE74C3C)
    }

    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, fontSize = 11.sp, fontWeight = FontWeight.Black, color = sharedState.themeTextColor)
            Text("$value/100", fontSize = 11.sp, fontWeight = FontWeight.Black, color = sharedState.themeTextColor)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .border(2.dp, sharedState.themeTextColor)
                .background(bgBoxColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(value / 100f)
                    .background(barColor)
                    .border(width = 1.dp, color = sharedState.themeTextColor)
            )
        }
    }
}