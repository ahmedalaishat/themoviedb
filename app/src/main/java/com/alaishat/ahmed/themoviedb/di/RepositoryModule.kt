package com.alaishat.ahmed.themoviedb.di

import com.alaishat.ahmed.themoviedb.data.mapper.MovieDetailsToDomainResolver
import com.alaishat.ahmed.themoviedb.data.repository.ConnectionRepositoryImpl
import com.alaishat.ahmed.themoviedb.data.repository.MoviesRepositoryImpl
import com.alaishat.ahmed.themoviedb.data.source.connection.ConnectionDataSource
import com.alaishat.ahmed.themoviedb.data.source.local.LocalMoviesDataSource
import com.alaishat.ahmed.themoviedb.data.source.remote.RemoteMoviesDataSource
import com.alaishat.ahmed.themoviedb.domain.repository.ConnectionRepository
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun bindsMoviesRepository(
        remoteMoviesDataSource: RemoteMoviesDataSource,
        localMoviesDataSource: LocalMoviesDataSource,
        movieDetailsToDomainResolver: MovieDetailsToDomainResolver,
        connectionRepository: ConnectionRepository,
        @ApplicationScope coroutineScope: CoroutineScope,
        @Dispatcher(AppDispatchers.IO) ioDispatcher: CoroutineDispatcher,
    ): MoviesRepository = MoviesRepositoryImpl(
        remoteMoviesDataSource = remoteMoviesDataSource,
        localMoviesDataSource = localMoviesDataSource,
        movieDetailsToDomainResolver = movieDetailsToDomainResolver,
        connectionRepository = connectionRepository,
        coroutineScope = coroutineScope,
        ioDispatcher = ioDispatcher
    )

    @Provides
    @Singleton
    fun bindsConnectionRepository(
        connectionDataSource: ConnectionDataSource,
        @Dispatcher(AppDispatchers.IO) ioDispatcher: CoroutineDispatcher,
    ): ConnectionRepository = ConnectionRepositoryImpl(
        connectionDataSource = connectionDataSource,
        ioDispatcher = ioDispatcher
    )

    @Provides
    fun providesMovieDetailsToDomainResolver() =
        MovieDetailsToDomainResolver()

}