package com.raul.parkdexnative.data

object CharacterImageMapper {
    fun getImageUrl(characterName: String): String {

        val formattedName = characterName.replace(" ", "_").replace("\"", "")
        val exceptions = mapOf(
            "Mr. Garrison" to "https://southpark.wiki.gg/wiki/Special:FilePath/MrGarrison.png", // Fara punct
            "Towelie" to "https://southpark.wiki.gg/wiki/Special:FilePath/Towelie.png",
            "Sheila Broflovski" to "https://southpark.wiki.gg/wiki/Special:FilePath/MrsBroflovski.png",
            "Kyle Broflovski" to "https://southpark.wiki.gg/wiki/Special:FilePath/Kyle-broflovski.png",
            "Ike Broflovski" to "https://southpark.wiki.gg/wiki/Special:FilePath/Ike-current.png",
            "Kyle's Elephant" to "https://southpark.wiki.gg/wiki/Special:FilePath/Elephant.png",
            "Cleo Broflovski" to "https://southpark.wiki.gg/wiki/Special:FilePath/CleoBroflovski.png",


        )

        if (exceptions.containsKey(characterName)) {
            return exceptions[characterName]!!
        }
        return "https://southpark.wiki.gg/wiki/Special:FilePath/$formattedName.png"
    }
}