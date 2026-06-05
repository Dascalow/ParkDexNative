package com.raul.parkdexnative.data

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.raul.parkdexnative.ui.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SharedState(private val context: Context) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val FAVORITES_KEY = stringPreferencesKey("favorite_characters")
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    val favoriteCharacters = mutableStateListOf<CharacterModel>()

    var appTheme by mutableStateOf("classic")
    var selectedCharacter by mutableStateOf<CharacterModel?>(null)
    var cheesyPoofsEaten by mutableStateOf(0)
    var accentColor by mutableStateOf(Color(0xFF17A2B8))

    init {
        loadFavoritesLocally()
    }

    // --- Salvare/Încărcare Locală (Offline) ---
    private fun loadFavoritesLocally() {
        scope.launch {
            try {
                val prefs = context.dataStore.data.first()
                val json = prefs[FAVORITES_KEY] ?: "[]"
                val list = Json.decodeFromString<List<CharacterModel>>(json)
                launch(Dispatchers.Main) {
                    favoriteCharacters.clear()
                    favoriteCharacters.addAll(list)
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    private fun saveFavoritesLocally() {
        scope.launch {
            val json = Json.encodeToString(favoriteCharacters.toList())
            context.dataStore.edit { it[FAVORITES_KEY] = json }
        }
    }

    // --- Salvare Cloud (Firebase) ---
    fun syncWithFirebase() {
        val user = auth.currentUser ?: return
        val data = mapOf("favorites" to favoriteCharacters.toList())
        firestore.collection("users").document(user.uid).set(data)
    }

    fun toggleFavorite(character: CharacterModel) {
        if (favoriteCharacters.any { it.id == character.id }) {
            favoriteCharacters.removeAll { it.id == character.id }
        } else {
            favoriteCharacters.add(character)
        }
        saveFavoritesLocally()
        syncWithFirebase()
    }

    fun isFavorite(character: CharacterModel): Boolean = favoriteCharacters.any { it.id == character.id }

    val themeBackgroundColor: Color
        get() = when (appTheme) {
            "dark" -> Color(0xFF1E1E1E)
            "classic" -> Color(0xFFFFCA28)
            else -> Color.White
        }

    val themeTextColor: Color
        get() = if (appTheme == "dark") Color.White else Color.Black
}