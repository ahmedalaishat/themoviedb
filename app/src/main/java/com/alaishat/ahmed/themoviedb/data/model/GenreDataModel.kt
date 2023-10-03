package com.alaishat.ahmed.themoviedb.data.model

import com.alaishat.ahmed.themoviedb.domain.model.GenreDomainModel

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
data class GenreDataModel(
    val id: Int,
    val name: String,
)

fun GenreDataModel.toGenreDomainModel() = GenreDomainModel(
    id = id,
    name = name,
)

fun List<GenreDataModel>.mapToGenresDomainModels() = map(GenreDataModel::toGenreDomainModel)
