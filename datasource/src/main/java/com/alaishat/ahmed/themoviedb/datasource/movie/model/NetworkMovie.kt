package com.alaishat.ahmed.themoviedb.datasource.movie.model

import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
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
    @SerialName("genre_ids") val genreIds: List<Int>? = emptyList(),
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

fun NetworkMovie.toMovieDataModel() = MovieDataModel(
    id = id,
    overview = overview,
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
)

fun List<NetworkMovie>.mapToMoviesDataModel() = map(NetworkMovie::toMovieDataModel)