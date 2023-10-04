package com.alaishat.ahmed.themoviedb.di

import com.alaishat.ahmed.themoviedb.data.mapper.MovieDetailsToDomainResolver
import com.alaishat.ahmed.themoviedb.data.repository.AccountRepositoryImpl
import com.alaishat.ahmed.themoviedb.data.repository.MoviesRepositoryImpl
import com.alaishat.ahmed.themoviedb.datasource.source.connection.datasource.ConnectionDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.local.LocalMoviesDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.remote.RemoteAccountDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.remote.RemoteMoviesDataSource
import com.alaishat.ahmed.themoviedb.domain.repository.AccountRepository
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
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
    fun bindsAccountRepository(
        remoteAccountDataSource: RemoteAccountDataSource,
        localMoviesDataSource: LocalMoviesDataSource,
        connectionDataSource: ConnectionDataSource,
        @Dispatcher(AppDispatchers.IO) ioDispatcher: CoroutineDispatcher,
    ): AccountRepository =
        AccountRepositoryImpl(
            remoteAccountDataSource = remoteAccountDataSource,
            localMoviesDataSource = localMoviesDataSource,
            connectionDataSource = connectionDataSource,
            ioDispatcher = ioDispatcher
        )

    @Provides
    @Singleton
    fun bindsMoviesRepository(
        remoteMoviesDataSource: RemoteMoviesDataSource,
        localMoviesDataSource: LocalMoviesDataSource,
        connectionDataSource: ConnectionDataSource,
        movieDetailsToDomainResolver: MovieDetailsToDomainResolver,
        @Dispatcher(AppDispatchers.IO) ioDispatcher: CoroutineDispatcher,
    ): MoviesRepository = MoviesRepositoryImpl(
        remoteMoviesDataSource = remoteMoviesDataSource,
        localMoviesDataSource = localMoviesDataSource,
        connectionDataSource = connectionDataSource,
        movieDetailsToDomainResolver = movieDetailsToDomainResolver,
        ioDispatcher = ioDispatcher
    )

    @Provides
    fun providesMovieDetailsToDomainResolver() = MovieDetailsToDomainResolver()

}