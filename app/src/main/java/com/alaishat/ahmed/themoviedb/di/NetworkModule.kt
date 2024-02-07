package com.alaishat.ahmed.themoviedb.di

import android.content.Context
import android.net.ConnectivityManager
import com.alaishat.ahmed.themoviedb.BuildConfig
import com.alaishat.ahmed.themoviedb.data.source.connection.ConnectionDataSource
import com.alaishat.ahmed.themoviedb.data.source.remote.RemoteMoviesDataSource
import com.alaishat.ahmed.themoviedb.datasource.connection.source.ConnectionDataSourceImpl
import com.alaishat.ahmed.themoviedb.datasource.movie.source.remote.KtorMoviesDataSource
import com.alaishat.ahmed.themoviedb.datasource.remote.KtorClient
import com.alaishat.ahmed.themoviedb.datasource.remote.error.KtorExceptionHandler
import com.alaishat.ahmed.themoviedb.datasource.remote.log.KtorLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.plugins.logging.LogLevel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesKtorClient(
        @Dispatcher(AppDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        networkJson: Json,
        ktorLogger: KtorLogger,
    ) = KtorClient(
        ioDispatcher = ioDispatcher,
        networkJson = networkJson,
        ktorLogger = ktorLogger,
        if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE
    )

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun providesKtorLogger() = KtorLogger()

    @Provides
    @Singleton
    fun providesMoviesDataSource(ktorClient: KtorClient): RemoteMoviesDataSource =
        KtorMoviesDataSource(ktorClient = ktorClient)

    @Provides
    fun providesConnectivityManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    @Provides
    @Singleton
    fun providesConnectionDataSource(
        connectivityManager: ConnectivityManager,
        @ApplicationScope coroutineScope: CoroutineScope,
        @Dispatcher(AppDispatchers.IO) ioDispatcher: CoroutineDispatcher,
    ): ConnectionDataSource =
        ConnectionDataSourceImpl(
            connectivityManager = connectivityManager,
            coroutineScope = coroutineScope,
            ioDispatcher = ioDispatcher,
        )

}