package com.raul.parkdexnative.data

object CharacterImageMapper {
    fun getImageUrl(characterName: String): String {
        // Aceste link-uri sunt de pe WIKIPEDIA, care NU ne blocheaza aplicatia!
        val specials = mapOf(
            "Eric Cartman" to "https://upload.wikimedia.org/wikipedia/en/7/77/EricCartman.png",
            "Stan Marsh" to "https://upload.wikimedia.org/wikipedia/en/a/a7/StanMarsh.png",
            "Kyle Broflovski" to "https://upload.wikimedia.org/wikipedia/en/2/25/KyleBroflovski.png",
            "Kenny McCormick" to "https://upload.wikimedia.org/wikipedia/en/6/6f/KennyMcCormick.png",
            "Butters Stotch" to "https://upload.wikimedia.org/wikipedia/en/0/06/ButtersStotch.png",
            "Randy Marsh" to "https://upload.wikimedia.org/wikipedia/en/b/b9/Randy_Marsh.png"
        )

        // Daca e unul din personajele principale, ii dam poza reala!
        if (specials.containsKey(characterName)) {
            return specials[characterName]!!
        }

        // Daca e alt personaj (si nu avem poza pentru el), ii generam un avatar cu initiale
        val formattedName = characterName.replace(" ", "+")
        val background = when {
            characterName.contains("Cartman") -> "E74C3C"
            characterName.contains("Stan") -> "3498DB"
            characterName.contains("Kyle") -> "2ECC71"
            characterName.contains("Kenny") -> "E67E22"
            characterName.contains("Marsh") -> "9B59B6"
            else -> "34495E"
        }

        return "https://ui-avatars.com/api/?name=$formattedName&background=$background&color=fff&size=256&bold=true&length=2"
    }
}