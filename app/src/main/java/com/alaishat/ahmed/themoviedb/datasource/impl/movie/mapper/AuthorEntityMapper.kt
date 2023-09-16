package com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper

import com.alaishat.ahmed.themoviedb.data.model.AuthorDetailsDataModel
import comalaishatahmedthemoviedbdatasourceimplsqldelight.AuthorEntity

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
fun AuthorDetailsDataModel.toEntity() = AuthorEntity(
    username = username,
    avatarPath = avatarPath,
    rating = rating?.toDouble(),
)