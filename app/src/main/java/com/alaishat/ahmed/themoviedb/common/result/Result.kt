package com.alaishat.ahmed.themoviedb.common.result

import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.common.usermessage.UserMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */

/**
 * A generic class that holds a value or an exception
 */
sealed interface Result<out T> {
    data class Success<out T>(val data: T) : Result<T>
    data class Error(val message: UserMessage) : Result<Nothing>
    object Loading : Result<Nothing>
}


fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> {
            Result.Success(it)
        }
        .onStart { emit(Result.Loading) }
        .catch { error ->
            val userMessage = error.message?.let { UserMessage.StringMessage(it) }
                ?: UserMessage.ResourceMessage(R.string.unknown_error)
            emit(Result.Error(userMessage))
        }
}