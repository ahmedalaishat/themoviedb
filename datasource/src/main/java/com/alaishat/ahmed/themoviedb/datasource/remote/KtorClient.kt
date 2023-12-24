package com.alaishat.ahmed.themoviedb.datasource.remote

import com.alaishat.ahmed.themoviedb.datasource.constants.BASE_URL
import com.alaishat.ahmed.themoviedb.datasource.constants.BREAR_TOKEN
import com.alaishat.ahmed.themoviedb.datasource.constants.REQUEST_TIME_OUT
import com.alaishat.ahmed.themoviedb.datasource.remote.error.KtorExceptionHandler
import com.alaishat.ahmed.themoviedb.datasource.remote.log.KtorLogger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
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
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

/**
 * Created by Ahmed Al-Aishat on Jun/24/2023.
 * The Movie DB Project.
 */
class KtorClient(
    val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val exceptionHandler: KtorExceptionHandler,
    private val ktorLogger: KtorLogger,
    private val logLevel: LogLevel,
) {

    val httpClient
        get() = HttpClient(Android) {
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

            HttpResponseValidator {
//                handleResponseExceptionWithRequest(exceptionHandler::handle)
            }
        }

    suspend inline fun <reified T> call(crossinline caller: suspend HttpClient.() -> HttpResponse): T {
        return withContext(ioDispatcher) {
            httpClient.use { client ->
                client.caller().body()
            }
        }
    }
}