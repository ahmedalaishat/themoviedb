package com.alaishat.ahmed.themoviedb.data.mapper

import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import com.alaishat.ahmed.themoviedb.domain.common.model.GenreDomainModel

/**
 * Created by Ahmed Al-Aishat on Feb/08/2024.
 * The Movie DB Project.
 */
fun GenreDataModel.toDomainModel() = GenreDomainModel(
    id = id,
    name = name,
)

fun List<GenreDataModel>.mapToDomainModels() = map(GenreDataModel::toDomainModel)