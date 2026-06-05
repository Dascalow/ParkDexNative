package com.raul.parkdexnative.data

object CharacterImageMapper {
    fun getImageUrl(characterName: String): String {
        val formattedName = characterName.replace(" ", "_")
        return "https://southpark.fandom.com/wiki/Special:FilePath/$formattedName.png"
    }
}