package com.alaishat.ahmed.themoviedb.datasource.impl.remote.error

import com.alaishat.ahmed.themoviedb.data.architecture.exception.DataException
import com.alaishat.ahmed.themoviedb.datasource.source.network.exception.ApiDataException
import com.alaishat.ahmed.themoviedb.datasource.source.network.exception.RequestFailedDataException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequest
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@Singleton
class KtorExceptionHandler @Inject constructor(private val networkJson: Json) {
    @Throws(DataException::class)
    suspend fun handle(cause: Throwable, request: HttpRequest) {
        val message = when (cause) {
            is UnknownHostException -> throw RequestFailedDataException()
            is SocketTimeoutException -> throw RequestFailedDataException()
            is ResponseException -> cause.response.decodeErrorMessage()
            else -> cause.message
        }
        Timber.tag("Ktor error:").e(message ?: "Unknown error")
        throw ApiDataException(message)
    }

    private suspend fun HttpResponse.decodeErrorMessage(): String? = try {
        networkJson.decodeFromString(ApiErrorBody.serializer(), bodyAsText()).message
    } catch (_: Exception) {
        null
    }
}