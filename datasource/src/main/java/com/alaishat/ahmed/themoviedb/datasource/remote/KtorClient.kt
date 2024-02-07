package com.alaishat.ahmed.themoviedb.datasource.remote

import com.alaishat.ahmed.themoviedb.data.architecture.DataResult
import com.alaishat.ahmed.themoviedb.data.source.remote.exception.ApiDataException
import com.alaishat.ahmed.themoviedb.data.source.remote.exception.ConnectionDataException
import com.alaishat.ahmed.themoviedb.data.source.remote.exception.UnknownDataException
import com.alaishat.ahmed.themoviedb.datasource.constants.BASE_URL
import com.alaishat.ahmed.themoviedb.datasource.constants.BREAR_TOKEN
import com.alaishat.ahmed.themoviedb.datasource.constants.REQUEST_TIME_OUT
import com.alaishat.ahmed.themoviedb.datasource.remote.log.KtorLogger
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.net.UnknownHostException

/**
 * Created by Ahmed Al-Aishat on Jun/24/2023.
 * The Movie DB Project.
 */
class KtorClient(
    val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val ktorLogger: KtorLogger,
    private val logLevel: LogLevel,
) {

    val httpClient = HttpClient(OkHttp.create()) {
        expectSuccess = true

        install(ContentNegotiation) {
            json(networkJson)
        }

        install(Logging) {
            logger = ktorLogger
            level = logLevel
        }

        install(HttpTimeout) {
            socketTimeoutMillis = REQUEST_TIME_OUT
            requestTimeoutMillis = REQUEST_TIME_OUT
            connectTimeoutMillis = REQUEST_TIME_OUT
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            url(BASE_URL)
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(BREAR_TOKEN, "")
                }
            }
        }
    }

    suspend inline fun <reified T : Any> safeApiCall(
        crossinline caller: suspend HttpClient.() -> HttpResponse,
    ): DataResult<T> = try {
        DataResult.Success(httpClient.caller().body())
    } catch (throwable: Throwable) {
        Timber.e(throwable)
        when (throwable) {
            is ServerResponseException, is NoTransformationFoundException -> DataResult.Error(
                ApiDataException(
                    throwable.message
                )
            )

            is ConnectTimeoutException, is UnknownHostException -> DataResult.Error(ConnectionDataException())

            else -> DataResult.Error(UnknownDataException())
        }
    }
}