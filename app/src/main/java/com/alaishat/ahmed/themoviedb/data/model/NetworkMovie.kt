package com.alaishat.ahmed.themoviedb.data.model

import com.alaishat.ahmed.themoviedb.domain.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@Serializable
data class NetworkMovie(
    @SerialName("adult") val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("genre_ids") val genreIds: List<Int>,
    @SerialName("id") val id: Int,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("overview") val overview: String,
    @SerialName("popularity") val popularity: Float,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String,//AHMED_TODO: convert me to date
    @SerialName("title") val title: String,
    @SerialName("video") val video: Boolean,
    @SerialName("vote_average") val voteAverage: Float,
    @SerialName("vote_count") val voteCount: Int,
)

fun List<NetworkMovie>.mapToMovies() = map {
    Movie(
        id = it.id,
        overview = it.overview,
        posterPath = it.posterPath.orEmpty(),
        releaseDate = it.releaseDate,
        title = it.title,
        voteAverage = "%.1f".format(it.voteAverage),
    )
}