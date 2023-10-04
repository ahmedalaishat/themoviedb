package com.alaishat.ahmed.themoviedb.di

import com.alaishat.ahmed.themoviedb.datasource.impl.account.datasource.remote.KtorAccountDataSource
import com.alaishat.ahmed.themoviedb.datasource.impl.connection.datasource.ConnectionDataSourceImpl
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.local.DelightLocalMovieDataSource
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.remote.KtorMoviesDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.connection.datasource.ConnectionDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.local.LocalMoviesDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.remote.RemoteAccountDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.remote.RemoteMoviesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Jun/24/2023.
 * The Movie DB Project.
 */
@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun providesAccountDataSource(
        accountDataSource: KtorAccountDataSource,
    ): RemoteAccountDataSource

    @Binds
    fun providesMoviesDataSource(
        moviesDataSource: KtorMoviesDataSource,
    ): RemoteMoviesDataSource

    @Binds
    fun providesLocalMoviesDataSource(
        delightLocalMovieDataSource: DelightLocalMovieDataSource,
    ): LocalMoviesDataSource

    @Binds
    @Singleton
    fun providesConnectionSDataSource(
        connectionDataSourceImpl: ConnectionDataSourceImpl,
    ): ConnectionDataSource
}