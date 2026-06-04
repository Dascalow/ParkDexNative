package com.raul.parkdexnative.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    @SerialName("data") val data: List<CharacterModel> = emptyList()
)

@Serializable
data class CharacterModel(
    @SerialName("id") val id: Int = 0,
    @SerialName("name") val name: String = "Unknown",
    @SerialName("sex") val sex: String? = null,
    @SerialName("religion") val religion: String? = null,
    @SerialName("url") val url: String? = null
)