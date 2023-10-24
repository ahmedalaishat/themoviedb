package com.alaishat.ahmed.themoviedb.datasource.movie.mapper

import com.alaishat.ahmed.themoviedb.data.model.AuthorDetailsDataModel
import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel
import comalaishatahmedthemoviedbdatasourcesqldelight.ReviewEntity
import comalaishatahmedthemoviedbdatasourcesqldelight.SelectMoviesReviewsPage

/**
 * Created by Ahmed Al-Aishat on Sep/15/2023.
 * The Movie DB Project.
 */
fun ReviewDataModel.toEntity(movieId: Int) = ReviewEntity(
    reviewId = id,
    movieId = movieId.toLong(),
    authorUserName = authorDetailsDataModel.username,
    content = content,
)

fun SelectMoviesReviewsPage.toReviewDataModel() = ReviewDataModel(
    id = reviewId,
    content = content.orEmpty(),
    authorDetailsDataModel = AuthorDetailsDataModel(
        username = username,
        avatarPath = avatarPath,
        rating = rating?.toFloat(),
    )
)