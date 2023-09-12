package com.alaishat.ahmed.themoviedb.datasource.impl.movie.model

import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Serializable
data class NetworkGenreModel(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
)

fun NetworkGenreModel.toGenreDataModel() = GenreDataModel(
    id = id,
    name = name,
)

fun List<NetworkGenreModel>.mapToGenesDataModels() = map(NetworkGenreModel::toGenreDataModel)