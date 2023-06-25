package com.alaishat.ahmed.themoviedb.di

import com.alaishat.ahmed.themoviedb.data.repository.MovieListRepositoryImpl
import com.alaishat.ahmed.themoviedb.domain.repository.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsMovieListRepository(
        movieListRepository: MovieListRepositoryImpl,
    ): MovieListRepository
}