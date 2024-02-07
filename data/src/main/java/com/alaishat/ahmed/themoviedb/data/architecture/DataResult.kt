package com.alaishat.ahmed.themoviedb.data.architecture

import com.alaishat.ahmed.themoviedb.data.architecture.exception.DataException
import com.alaishat.ahmed.themoviedb.data.mapper.toDomainException
import com.alaishat.ahmed.themoviedb.domain.achitecture.exception.DomainException

/**
 * Created by Ahmed Al-Aishat on Dec/24/2023.
 * The Movie DB Project.
 */
sealed interface DataResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : DataResult<T>
    data class Error(val exception: DataException? = null) : DataResult<Nothing>
}

fun <T : Any, R : Any> DataResult<T>.successMapper(
    mapper: (T) -> R,
): DataResult<R> = when (this) {
    is DataResult.Error -> this
    is DataResult.Success -> DataResult.Success(mapper(this.data))
}

fun <T : Any> DataResult<T>.getOrThrow(): T = when (this) {
    is DataResult.Error -> throw this.exception.toDomainException()
    is DataResult.Success -> this.data
}

fun <T : Any> DataResult<T>.getOrNull(): T? = when (this) {
    is DataResult.Error -> null
    is DataResult.Success -> this.data
}

//fun <T : Any, E : Throwable> DataResult<T>.getOrThrow(
//    exceptionMapper: (DataException?) -> E,
//): DataResult.Success<T> = when (this) {
//    is DataResult.Error -> throw exceptionMapper(this.exception)
//    is DataResult.Success -> this
//}
//
//suspend fun <T : Any> DataResult<T>.handleError(
//    handler: suspend (DomainException) -> T,
//): T = when (this) {
//    is DataResult.Error -> handler(this.exception.toDomainException())
//    is DataResult.Success -> this.data
//}
//
//fun <T : Any> DataResult<T>.onEach(
//    transform: (DataResult<T>) -> Unit,
//): DataResult<T> = this.also {
//    transform(this)
//}
//
//suspend fun <T : Any> DataResult<T>.onEachSuccess(
//    transform: suspend (T) -> Unit,
//): DataResult<T> = this.also {
//    if (this is DataResult.Success) transform(this.data)
//}