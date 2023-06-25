package com.alaishat.ahmed.themoviedb.network

import com.alaishat.ahmed.themoviedb.BuildConfig
import com.alaishat.ahmed.themoviedb.di.Dispatcher
import com.alaishat.ahmed.themoviedb.di.MovieDispatcher
import com.alaishat.ahmed.themoviedb.network.error.KtorExceptionHandler
import com.alaishat.ahmed.themoviedb.network.log.KtorLogger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
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
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Jun/24/2023.
 * The Movie DB Project.
 */
const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "09957daf31929ea5e3a7ae61108eb811"
const val API_KEY_PARAM = "api_key"
private const val TIME_OUT = 30_000L

@Singleton
class KtorClient @Inject constructor(
    @Dispatcher(MovieDispatcher.IO) val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val exceptionHandler: KtorExceptionHandler,
    private val ktorLogger: KtorLogger,
) {

    val httpClient
        get() = HttpClient(Android) {
            expectSuccess = true

            install(ContentNegotiation) {
                json(networkJson)
            }

            install(Logging) {
                logger = ktorLogger
                level = if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE
            }

            install(HttpTimeout) {
                socketTimeoutMillis = TIME_OUT
                requestTimeoutMillis = TIME_OUT
                connectTimeoutMillis = TIME_OUT
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                url(BASE_URL)
                url {
                    parameters.append(API_KEY_PARAM, API_KEY)
                }
            }

            HttpResponseValidator {
                handleResponseExceptionWithRequest(exceptionHandler::handle)
            }
        }

    suspend inline fun <reified T> call(crossinline caller: suspend HttpClient.() -> HttpResponse): T {
        return withContext(ioDispatcher) {
            httpClient.use { client -> client.caller().body() }
        }
    }
}