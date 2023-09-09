package com.alaishat.ahmed.themoviedb.domain.model

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
data class Review(
    val id: String,
    val content: String,
    val authorName: String,
    val authorAvatarPath: String?,
    val rating: String?,
)