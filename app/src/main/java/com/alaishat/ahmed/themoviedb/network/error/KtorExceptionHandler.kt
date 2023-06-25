package com.alaishat.ahmed.themoviedb.network.error

import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequest
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@Singleton
class KtorExceptionHandler @Inject constructor(private val networkJson: Json) {
    suspend fun handle(cause: Throwable, request: HttpRequest) {
        val message = when (cause) {
            is ResponseException -> cause.response.decodeErrorMessage()
            else -> cause.message
        }
        Timber.tag("Ktor error:").e(message ?: "Unknown error")
        throw ApiException(message)
    }

    private suspend fun HttpResponse.decodeErrorMessage(): String? = try {
        networkJson.decodeFromString(ApiErrorBody.serializer(), bodyAsText()).message
    } catch (_: Exception) {
        null
    }
}