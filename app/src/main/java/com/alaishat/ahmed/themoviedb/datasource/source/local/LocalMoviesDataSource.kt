package com.alaishat.ahmed.themoviedb.datasource.source.local

import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
interface LocalMoviesDataSource {
    fun getMovieGenreList(): Flow<List<GenreDataModel>>
    fun updateMovieGenreList(genreList: List<GenreDataModel>)
}