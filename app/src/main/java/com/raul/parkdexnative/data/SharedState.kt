package com.raul.parkdexnative.data

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val Context.dataStore by preferencesDataStore(name = "user_settings")

class SharedState(private val context: Context) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val currentUid: String
        get() = auth.currentUser?.uid ?: "guest"

    private val FAVORITES_KEY get() = stringPreferencesKey("favorite_characters_$currentUid")
    private val EPISODES_KEY get() = intPreferencesKey("episodes_seen_$currentUid")
    private val POOFS_KEY get() = intPreferencesKey("cheesy_poofs_eaten_$currentUid")
    private val THEME_KEY get() = stringPreferencesKey("app_theme_$currentUid")
    private val DOUCHEBAG_KEY get() = booleanPreferencesKey("is_douchebag_$currentUid")

    private val OFFLINE_CACHE_KEY = stringPreferencesKey("offline_characters_cache")

    val favoriteCharacters = mutableStateListOf<CharacterModel>()
    var episodesSeen by mutableStateOf(0)
    var cheesyPoofsEaten by mutableStateOf(0)
    var appTheme by mutableStateOf("classic")
    var isDouchebagMode by mutableStateOf(true)

    var offlineCharactersCache = mutableStateListOf<CharacterModel>()

    var selectedCharacter by mutableStateOf<CharacterModel?>(null)
    var accentColor by mutableStateOf(Color(0xFF17A2B8))

    init {
        CharacterImageMapper.loadAssets(context)
        CharacterBioMapper.loadLore(context)
        loadAllData()
    }

    fun loadAllData() {
        scope.launch {
            try {
                val prefs = context.dataStore.data.first()

                val jsonFavs = prefs[FAVORITES_KEY] ?: "[]"
                val list = try { Json.decodeFromString<List<CharacterModel>>(jsonFavs) } catch (e: Exception) { emptyList() }

                val savedEpisodes = prefs[EPISODES_KEY] ?: 0
                val savedPoofs = prefs[POOFS_KEY] ?: 0
                val savedTheme = prefs[THEME_KEY] ?: "classic"
                val savedDouchebag = prefs[DOUCHEBAG_KEY] ?: true

                val jsonCache = prefs[OFFLINE_CACHE_KEY] ?: "[]"
                val cacheList = try { Json.decodeFromString<List<CharacterModel>>(jsonCache) } catch (e: Exception) { emptyList() }

                launch(Dispatchers.Main) {
                    favoriteCharacters.clear()
                    favoriteCharacters.addAll(list)
                    episodesSeen = savedEpisodes
                    cheesyPoofsEaten = savedPoofs
                    appTheme = savedTheme
                    isDouchebagMode = savedDouchebag

                    offlineCharactersCache.clear()
                    offlineCharactersCache.addAll(cacheList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun saveOfflineCache(characters: List<CharacterModel>) {
        scope.launch {
            try {
                val json = Json.encodeToString(characters)
                context.dataStore.edit { prefs ->
                    prefs[OFFLINE_CACHE_KEY] = json
                }
                launch(Dispatchers.Main) {
                    offlineCharactersCache.clear()
                    offlineCharactersCache.addAll(characters)
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
        val currentDouchebag = isDouchebagMode

        scope.launch {
            try {
                val jsonFavs = Json.encodeToString(currentFavs)
                context.dataStore.edit { prefs ->
                    prefs[FAVORITES_KEY] = jsonFavs
                    prefs[EPISODES_KEY] = currentEps
                    prefs[POOFS_KEY] = currentPoofs
                    prefs[THEME_KEY] = currentTheme
                    prefs[DOUCHEBAG_KEY] = currentDouchebag
                }

                val user = auth.currentUser
                if (user != null) {
                    val firebaseFavs = currentFavs.map { char ->
                        mapOf("id" to char.id, "name" to char.name, "sex" to char.sex, "religion" to char.religion, "url" to char.url)
                    }
                    val data = mapOf(
                        "favorites" to firebaseFavs, "episodesSeen" to currentEps,
                        "cheesyPoofsEaten" to currentPoofs, "appTheme" to currentTheme,
                        "isDouchebagMode" to currentDouchebag
                    )
                    firestore.collection("users").document(user.uid).set(data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleFavorite(character: CharacterModel) {
        if (favoriteCharacters.any { it.id == character.id }) favoriteCharacters.removeAll { it.id == character.id }
        else favoriteCharacters.add(character)
        saveProgress()
    }

    fun isFavorite(character: CharacterModel): Boolean = favoriteCharacters.any { it.id == character.id }

    val themeBackgroundColor: Color get() = when (appTheme) { "dark" -> Color(0xFF1E1E1E); "classic" -> Color(0xFFFFCA28); else -> Color.White }
    val themeTextColor: Color get() = if (appTheme == "dark") Color.White else Color.Black
}