package com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper

import com.alaishat.ahmed.themoviedb.data.model.CreditDataModel
import comalaishatahmedthemoviedbdatasourceimplsqldelight.CreditEntity

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
fun CreditDataModel.toEntity(movieId: Int) = CreditEntity(
    creditId = creditId,
    movieId = movieId.toLong(),
    name = name,
    profilePath = profilePath,
)

fun CreditEntity.toDataModel() = CreditDataModel(
    creditId = creditId,
    name = name,
    profilePath = profilePath,
)