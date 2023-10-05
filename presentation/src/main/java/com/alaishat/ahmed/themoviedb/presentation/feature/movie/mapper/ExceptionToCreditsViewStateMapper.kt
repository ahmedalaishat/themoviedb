package com.alaishat.ahmed.themoviedb.presentation.feature.movie.mapper

import com.alaishat.ahmed.themoviedb.domain.achitecture.exception.DomainException
import com.alaishat.ahmed.themoviedb.domain.achitecture.exception.FetchFailedDomainException
import com.alaishat.ahmed.themoviedb.presentation.feature.movie.model.CreditsError

/**
 * Created by Ahmed Al-Aishat on Sep/26/2023.
 * The Movie DB Project.
 */
fun DomainException.toCreditsViewError() = when (this) {
    is FetchFailedDomainException -> CreditsError.Failed
    else -> CreditsError.Unknown
}