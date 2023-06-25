package com.alaishat.ahmed.themoviedb.di

import com.alaishat.ahmed.themoviedb.data.source.network.NetworkMovieListsDataSource
import com.alaishat.ahmed.themoviedb.network.datasource.KtorMovieListsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Ahmed Al-Aishat on Jun/24/2023.
 * The Movie DB Project.
 */
@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    fun providesMovieListDataSource(
        movieListsDataSource: KtorMovieListsDataSource
    ): NetworkMovieListsDataSource
}