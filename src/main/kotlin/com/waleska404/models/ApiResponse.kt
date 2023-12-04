package com.waleska404.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val success: Boolean,
    val message: String? = null,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
    val characters: List<Character> = emptyList(),
    val lastUpdated: Long? = null,
)
