package com.alaishat.ahmed.themoviedb.data.repository

import com.alaishat.ahmed.themoviedb.data.model.mapToMovies
import com.alaishat.ahmed.themoviedb.data.source.network.NetworkMoviesDataSource
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.model.MovieListType
import com.alaishat.ahmed.themoviedb.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class MovieListRepositoryImpl @Inject constructor(
    private val movieListsDataSource: NetworkMoviesDataSource
) : MovieListRepository {

    override fun getMovieListByType(movieListType: MovieListType): Flow<List<Movie>> = flow {
        val movieListPath = when (movieListType) {
            MovieListType.NOW_PLAYING -> "now_playing"
            MovieListType.POPULAR -> "popular"
            MovieListType.TOP_RATED -> "top_rated"
            MovieListType.UPCOMING -> "upcoming"
        }
        val movies = movieListsDataSource.getList(movieListPath).mapToMovies()
        emit(movies)
    }

    override fun searchMovie(query: String): Flow<List<Movie>> = flow {
        val movies = movieListsDataSource.searchMovie(query).mapToMovies()
        emit(movies)
    }

    override fun getWatchList(): Flow<List<Movie>> = flow {
        val movies = movieListsDataSource.getWatchList().mapToMovies()
        emit(movies)
    }
}