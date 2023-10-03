package com.alaishat.ahmed.themoviedb.datasource.impl.movie.model

import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
@Serializable
data class NetworkGenre(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
)

fun NetworkGenre.toGenreDataModel() = GenreDataModel(
    id = id,
    name = name,
)

fun List<NetworkGenre>.mapToGenresDataModels() = map(NetworkGenre::toGenreDataModel)