package com.raul.parkdexnative.data

import kotlinx.serialization.Serializable

@Serializable
data class LoreStats(
    val Cursing: Int = 0,
    val Manipulation: Int = 0,
    val Empathy: Int = 0,
    val Athleticism: Int = 0
)

@Serializable
data class LoreEntry(
    val tags: List<String> = emptyList(),
    val shortDesc: String = "",
    val bio: String = "",
    val alias: String = "",
    val age: String = "",
    val grade: String = "",
    val voicedBy: String = "",
    val stats: LoreStats = LoreStats(),
    val quotes: List<String> = emptyList()
)