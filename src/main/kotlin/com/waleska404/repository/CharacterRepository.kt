package com.waleska404.repository

import com.waleska404.models.ApiResponse
import com.waleska404.models.Character

interface CharacterRepository {

    val characters: Map<Int, List<Character>>

    val page1: List<Character>
    val page2: List<Character>
    val page3: List<Character>
    val page4: List<Character>
    val page5: List<Character>

    suspend fun getAllCharacters(page: Int = 1): ApiResponse
    suspend fun searchCharacters(name: String): ApiResponse
}