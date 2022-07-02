package io.github.brunogabriel.sample

class CharacterRepository(
    private val characterService: CharacterService
) {
    suspend fun getCharacters() = characterService.getCharacters()
    suspend fun getMockCharacters() = characterService.getMockCharacters()
}