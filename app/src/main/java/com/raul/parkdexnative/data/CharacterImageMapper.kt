package com.raul.parkdexnative.data

object CharacterImageMapper {    fun getImageUrl(characterName: String): String {
    // Personajele principale au link-uri "directe" garantate
    val specials = mapOf(
        "Eric Cartman" to "https://static.wikia.nocookie.net/southpark/images/7/77/EricCartman.png",
        "Stan Marsh" to "https://static.wikia.nocookie.net/southpark/images/a/a7/StanMarsh.png",
        "Kyle Broflovski" to "https://static.wikia.nocookie.net/southpark/images/2/25/KyleBroflovski.png",
        "Kenny McCormick" to "https://static.wikia.nocookie.net/southpark/images/6/6f/KennyMcCormick.png",
        "Butters Stotch" to "https://static.wikia.nocookie.net/southpark/images/0/06/ButtersStotch.png",
        "Randy Marsh" to "https://static.wikia.nocookie.net/southpark/images/b/b9/Randy_Marsh.png",
        "Tolkien Black" to "https://static.wikia.nocookie.net/southpark/images/7/7d/Token_Black.png"
    )

    if (specials.containsKey(characterName)) return specials[characterName]!!

    // Pentru restul, folosim o variantă mai "agresivă" de curățare a numelui
    val formattedName = characterName.replace(" ", "_")

    // Trick: Adăugăm un parametru de lățime pentru a forța Wiki-ul să genereze un fișier static
    return "https://southpark.fandom.com/wiki/Special:FilePath/$formattedName.png?width=300"
}
}