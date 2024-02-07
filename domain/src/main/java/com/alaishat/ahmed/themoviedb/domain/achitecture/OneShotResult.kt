package com.alaishat.ahmed.themoviedb.domain.achitecture

import com.alaishat.ahmed.themoviedb.domain.achitecture.exception.DomainException

/**
 * Created by Ahmed Al-Aishat on Dec/24/2023.
 * The Movie DB Project.
 */
sealed interface OneShotResult<out R> {
    data class Success<out T>(val data: T) : OneShotResult<T>
    data class Error(val cause: DomainException) : OneShotResult<Nothing>
}

fun <T> OneShotResult<T>.successOr(fallback: T): T {
    return (this as? OneShotResult.Success<T>)?.data ?: fallback
}

fun <T : Any> OneShotResult<T>.getOrThrow(): OneShotResult.Success<T> = when (this) {
    is OneShotResult.Error -> throw this.cause
    is OneShotResult.Success -> this
}