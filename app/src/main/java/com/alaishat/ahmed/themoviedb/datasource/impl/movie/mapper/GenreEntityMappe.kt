package com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper

import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import comalaishatahmedthemoviedbdatasourceimplsqldelight.GenreEntity

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
fun GenreEntity.toGenreDataModel() = GenreDataModel(
    id = genreId.toInt(),
    name = name,
)

fun List<GenreEntity>.mapToGenreDataModel() = map(GenreEntity::toGenreDataModel)


fun GenreDataModel.asEntity() = GenreEntity(
    genreId = id.toLong(),
    name = name,
)