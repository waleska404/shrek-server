package com.waleska404.repository

import com.waleska404.models.ApiResponse
import com.waleska404.models.Character

interface CharacterRepositoryAlternative {

    val characters: List<Character>

    suspend fun getAllCharacters(page: Int = 1, limit: Int = 4): ApiResponse
    suspend fun searchCharacters(name: String?): ApiResponse
}