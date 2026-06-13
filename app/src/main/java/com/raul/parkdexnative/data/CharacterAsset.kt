package com.raul.parkdexnative.data

import kotlinx.serialization.Serializable

@Serializable
data class CharacterAsset(
    val name: String = "",
    val imageUrl: String = "",
    val wikiUrl: String = "")
