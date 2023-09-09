package com.alaishat.ahmed.themoviedb.data.model

import com.alaishat.ahmed.themoviedb.domain.model.Review
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Serializable
data class NetworkReview(
    @SerialName("id") val id: String,
    @SerialName("content") val content: String,
    @SerialName("author_details") val authorDetails: AuthorDetails,
)

fun NetworkReview.toReview() = Review(
    id = id,
    content = content,
    authorName = authorDetails.username,
    authorAvatarPath = authorDetails.avatarPath,
    rating = "%.1f".format(authorDetails.rating),
)

fun List<NetworkReview>.mapToReviews() = map(NetworkReview::toReview)