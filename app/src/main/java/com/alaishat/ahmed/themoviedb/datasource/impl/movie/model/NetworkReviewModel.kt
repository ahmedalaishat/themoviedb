package com.alaishat.ahmed.themoviedb.datasource.impl.movie.model

import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Serializable
data class NetworkReviewModel(
    @SerialName("id") val id: String,
    @SerialName("content") val content: String,
    @SerialName("author_details") val authorDetails: NetworkAuthorDetailsModel,
)

fun NetworkReviewModel.toReviewDataModel() = ReviewDataModel(
    id = id,
    content = content,
    authorDetailsDataModel = authorDetails.toAuthorDetailsDataModel()
)

fun List<NetworkReviewModel>.mapToReviewsDataModels() = map(NetworkReviewModel::toReviewDataModel)