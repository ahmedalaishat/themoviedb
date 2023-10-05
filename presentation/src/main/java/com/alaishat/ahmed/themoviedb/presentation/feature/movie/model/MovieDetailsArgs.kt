package com.alaishat.ahmed.themoviedb.presentation.feature.movie.model

import androidx.lifecycle.SavedStateHandle

/**
 * Created by Ahmed Al-Aishat on Oct/06/2023.
 * The Movie DB Project.
 */
const val movieIdArg = "movie_id"

class MovieDetailsArgs(val movieId: Int) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(movieId = checkNotNull(savedStateHandle.get<Int>(movieIdArg)))
}