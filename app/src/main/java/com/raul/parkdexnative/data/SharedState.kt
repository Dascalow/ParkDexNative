package com.raul.parkdexnative.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

class SharedState {
    val favoriteCharacters = mutableStateListOf<CharacterModel>()
    var episodesSeen by mutableStateOf(0)
    var cheesyPoofsEaten by mutableStateOf(0)

    var selectedCharacter by mutableStateOf<CharacterModel?>(null)

    var appTheme by mutableStateOf("classic")
    var accentColor by mutableStateOf(Color(0xFF17A2B8))

    // Culorile care se schimba in functie de tema
    val themeBackgroundColor: Color
        get() = when (appTheme) {
            "dark" -> Color(0xFF1E1E1E) // Gri inchis
            "classic" -> Color(0xFFFFCA28) // Galben South Park
            else -> Color.White // Light
        }

    val themeTextColor: Color
        get() = when (appTheme) {
            "dark" -> Color.White
            else -> Color.Black
        }

    fun toggleFavorite(character: CharacterModel) {
        if (favoriteCharacters.any { it.id == character.id }) {
            favoriteCharacters.removeAll { it.id == character.id }
        } else {
            favoriteCharacters.add(character)
        }
    }

    fun isFavorite(character: CharacterModel): Boolean {
        return favoriteCharacters.any { it.id == character.id }
    }
}