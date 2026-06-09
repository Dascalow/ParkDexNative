package com.raul.parkdexnative.data

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore // Am adaugat importul corect aici
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// DEFINIM DATASTORE AICI GLOBAL, PE FIECARE TELEFON.
// Astfel nu mai avem nevoie de niciun import din folderul UI!
val Context.dataStore by preferencesDataStore(name = "user_settings")

class SharedState(private val context: Context) {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val FAVORITES_KEY = stringPreferencesKey("favorite_characters")
    private val EPISODES_KEY = intPreferencesKey("episodes_seen")
    private val POOFS_KEY = intPreferencesKey("cheesy_poofs_eaten")
    private val THEME_KEY = stringPreferencesKey("app_theme")

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    val favoriteCharacters = mutableStateListOf<CharacterModel>()
    var episodesSeen by mutableStateOf(0)
    var cheesyPoofsEaten by mutableStateOf(0)
    var appTheme by mutableStateOf("classic")

    var selectedCharacter by mutableStateOf<CharacterModel?>(null)
    var accentColor by mutableStateOf(Color(0xFF17A2B8))

    init {
        loadAllData()
    }

    private fun loadAllData() {
        scope.launch {
            try {
                context.dataStore.data.collect { prefs ->
                    val jsonFavs = prefs[FAVORITES_KEY] ?: "[]"
                    val list = Json.decodeFromString<List<CharacterModel>>(jsonFavs)
                    val savedEpisodes = prefs[EPISODES_KEY] ?: 0
                    val savedPoofs = prefs[POOFS_KEY] ?: 0
                    val savedTheme = prefs[THEME_KEY] ?: "classic"

                    launch(Dispatchers.Main) {
                        if (favoriteCharacters.size != list.size) {
                            favoriteCharacters.clear()
                            favoriteCharacters.addAll(list)
                        }
                        if (episodesSeen != savedEpisodes) episodesSeen = savedEpisodes
                        if (cheesyPoofsEaten != savedPoofs) cheesyPoofsEaten = savedPoofs
                        if (appTheme != savedTheme) appTheme = savedTheme
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveProgress() {
        val currentFavs = favoriteCharacters.toList()
        val currentEps = episodesSeen
        val currentPoofs = cheesyPoofsEaten
        val currentTheme = appTheme

        scope.launch {
            try {
                // Acum aplicatia va gasi baza de date locala si va salva pe bune!
                val jsonFavs = Json.encodeToString(currentFavs)
                context.dataStore.edit { prefs ->
                    prefs[FAVORITES_KEY] = jsonFavs
                    prefs[EPISODES_KEY] = currentEps
                    prefs[POOFS_KEY] = currentPoofs
                    prefs[THEME_KEY] = currentTheme
                }

                val user = auth.currentUser
                if (user != null) {
                    val firebaseFavs = currentFavs.map { char ->
                        mapOf(
                            "id" to char.id,
                            "name" to char.name,
                            "sex" to char.sex,
                            "religion" to char.religion,
                            "url" to char.url
                        )
                    }

                    val data = mapOf(
                        "favorites" to firebaseFavs,
                        "episodesSeen" to currentEps,
                        "cheesyPoofsEaten" to currentPoofs,
                        "appTheme" to currentTheme
                    )
                    firestore.collection("users").document(user.uid).set(data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleFavorite(character: CharacterModel) {
        if (favoriteCharacters.any { it.id == character.id }) {
            favoriteCharacters.removeAll { it.id == character.id }
        } else {
            favoriteCharacters.add(character)
        }
        saveProgress()
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