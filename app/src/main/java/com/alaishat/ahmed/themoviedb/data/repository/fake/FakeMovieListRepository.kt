package com.alaishat.ahmed.themoviedb.data.repository.fake

import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.model.MovieListType
import com.alaishat.ahmed.themoviedb.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class FakeMovieListRepository : MovieListRepository {
    override fun getMovieListByType(movieListType: MovieListType): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }

    override fun searchMovie(query: String): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }
}