package com.alaishat.ahmed.themoviedb.presentation.feature.movie.mapper

import com.alaishat.ahmed.themoviedb.domain.achitecture.exception.DomainException
import com.alaishat.ahmed.themoviedb.domain.achitecture.exception.FetchFailedDomainException
import com.alaishat.ahmed.themoviedb.presentation.feature.movie.model.MovieDetailsError

/**
 * Created by Ahmed Al-Aishat on Sep/21/2023.
 * The Movie DB Project.
 */
fun DomainException.toViewError() = when (this) {
    is FetchFailedDomainException -> MovieDetailsError.Failed
    else -> MovieDetailsError.Unknown
}