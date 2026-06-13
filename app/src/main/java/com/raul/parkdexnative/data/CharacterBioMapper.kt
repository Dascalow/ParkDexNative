package com.raul.parkdexnative.data

import android.content.Context
import kotlinx.serialization.json.Json

object CharacterBioMapper {
    private var loreMap: Map<String, LoreEntry> = emptyMap()

    fun loadLore(context: Context) {
        if (loreMap.isNotEmpty()) return

        try {
            val jsonString = context.assets.open("lore.json").bufferedReader().use { it.readText() }
            val jsonParser = Json { ignoreUnknownKeys = true }
            loreMap = jsonParser.decodeFromString<Map<String, LoreEntry>>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getLore(characterName: String): LoreEntry? {
        return loreMap[characterName.lowercase()]
    }

    fun getBio(characterName: String): String {
        val entry = getLore(characterName)
        return entry?.bio ?: "Data from SPAPI does not include a full biography for this character yet. They are a resident of South Park, Colorado."
    }
}