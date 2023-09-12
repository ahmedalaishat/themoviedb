package com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper.mapToGenreDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper.asEntity
import com.alaishat.ahmed.themoviedb.datasource.source.local.LocalMoviesDataSource
import comalaishatahmedthemoviedbdatasourceimplsqldelight.GenreEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
@Singleton
class DelightLocalMovieDataSource @Inject constructor(
    private val genreEntityQueries: GenreEntityQueries,
) : LocalMoviesDataSource {
    override fun getMovieGenreList(): Flow<List<GenreDataModel>> {
        return genreEntityQueries.selectAll().asFlow().mapToList(Dispatchers.IO).map {
            it.mapToGenreDataModel()
        }
    }

    override fun updateMovieGenreList(genreList: List<GenreDataModel>) {
        genreEntityQueries.transaction {
            genreEntityQueries.deleteAll()
            genreList.forEach { genre ->
                genreEntityQueries.insertFullGenreObject(genre.asEntity())
            }
        }
    }
}