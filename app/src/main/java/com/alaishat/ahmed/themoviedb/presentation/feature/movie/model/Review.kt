package com.alaishat.ahmed.themoviedb.presentation.feature.movie.model

import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.ReviewDomainModel

/**
 * Created by Ahmed Al-Aishat on Sep/26/2023.
 * The Movie DB Project.
 */
data class Review(
    val id: String,
    val content: String,
    val authorName: String,
    val authorAvatarPath: String?,
    val rating: String?,
)

fun ReviewDomainModel.toPresentation() = Review(
    id = id,
    content = content,
    authorName = authorName,
    authorAvatarPath = authorAvatarPath,
    rating = rating,
)