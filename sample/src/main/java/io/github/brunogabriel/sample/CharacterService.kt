package io.github.brunogabriel.sample

import retrofit2.http.GET

interface CharacterService {
    @GET("character")
    suspend fun getCharacters(): String

    @GET("character")
    suspend fun getMockCharacters(): String
}