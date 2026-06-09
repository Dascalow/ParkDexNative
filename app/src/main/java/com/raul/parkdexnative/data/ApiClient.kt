package com.raul.parkdexnative.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ApiClient {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
    }

    suspend fun getCharacters(): List<CharacterModel> {
        val allCharacters = mutableListOf<CharacterModel>()
        for (page in 1..7) {
            try {
                val response: ApiResponse = client.get("https://spapi.dev/api/characters?page=$page").body()
                allCharacters.addAll(response.data)
                if (allCharacters.size >= 100) {
                    break
                }
            } catch (e: Exception) {
                e.printStackTrace()
                break
            }
        }
        return allCharacters.take(100)
    }
}