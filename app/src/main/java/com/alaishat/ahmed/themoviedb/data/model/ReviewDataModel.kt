package com.alaishat.ahmed.themoviedb.data.model

import com.alaishat.ahmed.themoviedb.domain.model.ReviewDomainModel

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
data class ReviewDataModel(
    val id: String,
    val content: String,
    val authorDetailsDataModel: AuthorDetailsDataModel,
)

fun ReviewDataModel.toReviewDomainModel() = ReviewDomainModel(
    id = id,
    content = content,
    authorName = authorDetailsDataModel.username,
    authorAvatarPath = authorDetailsDataModel.avatarPath,
    rating = authorDetailsDataModel.rating?.let { "%.1f".format(it) },
)

fun List<ReviewDataModel>.mapToReviewsDomainModels() = map(ReviewDataModel::toReviewDomainModel)