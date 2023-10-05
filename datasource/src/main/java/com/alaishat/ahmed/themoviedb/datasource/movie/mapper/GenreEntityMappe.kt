package com.alaishat.ahmed.themoviedb.datasource.movie.mapper

import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import comalaishatahmedthemoviedbdatasourcesqldelight.GenreEntity

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
fun GenreEntity.toGenreDataModel() = GenreDataModel(
    id = genreId.toInt(),
    name = name,
)

fun List<GenreEntity>.mapToGenreDataModels() = map(GenreEntity::toGenreDataModel)


fun GenreDataModel.toEntity() = GenreEntity(
    genreId = id.toLong(),
    name = name,
)