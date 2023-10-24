package com.alaishat.ahmed.themoviedb.datasource.movie.model

import com.alaishat.ahmed.themoviedb.data.model.MovieDetailsDataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Serializable
data class NetworkMovieDetails(
    @SerialName("adult") val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("genres") val genreDataModels: List<NetworkGenreModel>,
    @SerialName("id") val id: Int,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("overview") val overview: String,
    @SerialName("popularity") val popularity: Float,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String,//AHMED_TODO: convert me to date
    @SerialName("runtime") val runtime: Int,
    @SerialName("title") val title: String,
    @SerialName("video") val video: Boolean,
    @SerialName("vote_average") val voteAverage: Float,
    @SerialName("vote_count") val voteCount: Int,
)


fun NetworkMovieDetails.toMoviesDetailsDataModel() = MovieDetailsDataModel(
    backdropPath = backdropPath.orEmpty(),
    genreDataModels = genreDataModels.mapToGenesDataModels(),
    id = id,
    overview = overview,
    posterPath = posterPath.orEmpty(),
    releaseDate = releaseDate.split("-").first(),
    runtime = runtime,
    title = title,
    voteAverage = voteAverage,
)