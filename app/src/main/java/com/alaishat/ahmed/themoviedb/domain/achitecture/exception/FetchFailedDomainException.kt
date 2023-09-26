package com.alaishat.ahmed.themoviedb.domain.achitecture.exception

import com.alaishat.ahmed.themoviedb.domain.achitecture.exception.DomainException

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
class FetchFailedDomainException(throwable: Throwable) : DomainException(throwable)
