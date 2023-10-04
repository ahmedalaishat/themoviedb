package com.alaishat.ahmed.themoviedb.di

import android.content.Context
import android.net.ConnectivityManager
import com.alaishat.ahmed.themoviedb.datasource.impl.account.datasource.remote.KtorAccountDataSource
import com.alaishat.ahmed.themoviedb.datasource.impl.connection.datasource.ConnectionDataSourceImpl
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.remote.KtorMoviesDataSource
import com.alaishat.ahmed.themoviedb.datasource.impl.remote.KtorClient
import com.alaishat.ahmed.themoviedb.datasource.impl.remote.error.KtorExceptionHandler
import com.alaishat.ahmed.themoviedb.datasource.impl.remote.log.KtorLogger
import com.alaishat.ahmed.themoviedb.datasource.source.connection.datasource.ConnectionDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.remote.RemoteAccountDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.remote.RemoteMoviesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
        exceptionHandler: KtorExceptionHandler,
        ktorLogger: KtorLogger,
    ) = KtorClient(
        ioDispatcher = ioDispatcher,
        networkJson = networkJson,
        exceptionHandler = exceptionHandler,
        ktorLogger = ktorLogger,
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
    fun providesKtorExceptionHandler(
        networkJson: Json,
    ) = KtorExceptionHandler(networkJson)

    @Provides
    @Singleton
    fun providesKtorLogger() = KtorLogger()

    @Provides
    @Singleton
    fun providesAccountDataSource(ktorClient: KtorClient): RemoteAccountDataSource =
        KtorAccountDataSource(ktorClient = ktorClient)

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
    ): ConnectionDataSource = ConnectionDataSourceImpl(
        connectivityManager = connectivityManager,
        coroutineScope = coroutineScope,
        ioDispatcher = ioDispatcher,
    )

}