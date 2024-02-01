package com.alaishat.ahmed.themoviedb.datasource.remote.error

import com.alaishat.ahmed.themoviedb.data.architecture.exception.DataException
import com.alaishat.ahmed.themoviedb.data.source.remote.exception.ApiDataException
import com.alaishat.ahmed.themoviedb.data.source.remote.exception.ConnectionDataException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequest
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class KtorExceptionHandler(private val networkJson: Json) {
    @Throws(DataException::class)
    suspend fun handle(cause: Throwable, request: HttpRequest) {
        val message = when (cause) {
            is UnknownHostException -> throw ConnectionDataException()
            is SocketTimeoutException -> throw ConnectionDataException()
            is ResponseException -> cause.response.decodeErrorMessage()
            else -> cause.message
        }
        Timber.tag("Ktor error:").e(message ?: "Unknown error")
        throw ApiDataException(message)
    }

    private suspend fun HttpResponse.decodeErrorMessage(): String? = try {
        networkJson.decodeFromString(
            ApiErrorBody.serializer(),
            bodyAsText()
        ).message
    } catch (_: Exception) {
        null
    }
}