package io.github.brunogabriel.sample

import io.github.brunogabriel.mockpinterceptor.MOCK
import retrofit2.http.GET

interface CharacterService {
    @GET("character")
    suspend fun getCharacters(): String

    @MOCK(asset = "mock-response.json", runDelay = true)
    @GET("character")
    suspend fun getMockCharacters(): String
}