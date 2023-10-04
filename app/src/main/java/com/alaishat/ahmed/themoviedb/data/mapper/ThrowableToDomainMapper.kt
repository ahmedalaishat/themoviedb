package com.alaishat.ahmed.themoviedb.data.mapper

import com.alaishat.ahmed.themoviedb.datasource.source.remote.exception.ApiDataException
import com.alaishat.ahmed.themoviedb.datasource.source.remote.exception.RequestFailedDataException
import com.alaishat.ahmed.themoviedb.domain.achitecture.exception.DomainException
import com.alaishat.ahmed.themoviedb.domain.achitecture.exception.UnknownDomainException
import com.alaishat.ahmed.themoviedb.domain.achitecture.exception.FetchFailedDomainException

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
fun Throwable.toDomainException(): DomainException = when (this) {
    is ApiDataException -> FetchFailedDomainException(this)
    is RequestFailedDataException -> FetchFailedDomainException(this)
    else -> UnknownDomainException(this)
}