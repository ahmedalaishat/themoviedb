package com.alaishat.ahmed.themoviedb.data.feature.movie.mapper

import com.alaishat.ahmed.themoviedb.datasource.source.network.exception.ApiDataException
import com.alaishat.ahmed.themoviedb.datasource.source.network.exception.RequestTimeoutDataException
import com.alaishat.ahmed.themoviedb.domain.achitecture.exception.DomainException
import com.alaishat.ahmed.themoviedb.domain.achitecture.exception.UnknownDomainException
import com.alaishat.ahmed.themoviedb.domain.feature.movie.exception.FetchFailedDomainException

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
fun Throwable.toMovieDomainException(): DomainException = when (this) {
    is ApiDataException -> FetchFailedDomainException(this)
    is RequestTimeoutDataException -> FetchFailedDomainException(this)
    else -> UnknownDomainException(this)
}