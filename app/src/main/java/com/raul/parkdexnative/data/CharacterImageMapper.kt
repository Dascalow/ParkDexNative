package com.raul.parkdexnative.data

import android.content.Context
import kotlinx.serialization.json.Json

object CharacterImageMapper {
    private var assetMap: Map<String, CharacterAsset> = emptyMap()

    fun loadAssets(context: Context) {
        if (assetMap.isNotEmpty()) return
        try {
            val jsonString = context.assets.open("characters_assets.json").bufferedReader().use { it.readText() }

            val jsonParser = Json { ignoreUnknownKeys = true }
            assetMap = jsonParser.decodeFromString<Map<String, CharacterAsset>>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getImageUrl(characterId: Int, fallbackName: String): String {
        val asset = assetMap[characterId.toString()]

        if (asset != null && asset.imageUrl.isNotEmpty()) {
            return if (asset.imageUrl.startsWith("/")) {
                "https://southpark.wiki.gg${asset.imageUrl}"
            } else {
                asset.imageUrl
            }
        }
        val formattedName = fallbackName.replace(" ", "_").replace("\"", "")
        return "https://southpark.wiki.gg/wiki/Special:FilePath/$formattedName.png"
    }
}