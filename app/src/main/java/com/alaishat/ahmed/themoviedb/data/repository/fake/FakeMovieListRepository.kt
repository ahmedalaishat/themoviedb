package com.alaishat.ahmed.themoviedb.data.repository.fake

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.domain.model.Credit
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.model.MovieDetails
import com.alaishat.ahmed.themoviedb.domain.model.MovieListType
import com.alaishat.ahmed.themoviedb.domain.model.Review
import com.alaishat.ahmed.themoviedb.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class FakeMovieListRepository : MovieListRepository {
    override suspend fun getMoviesPageByType(movieListType: MovieListType, page: Int): List<Movie> {
        TODO("Not yet implemented")
    }

    override fun getMoviesPagingFlowByType(movieListType: MovieListType): Flow<PagingData<Movie>> {
        TODO("Not yet implemented")
    }

    override fun getSearchMoviePagingFlow(query: String): Flow<PagingData<Movie>> {
        TODO("Not yet implemented")
    }

    override fun getWatchListPagingFlow(): Flow<PagingData<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        TODO("Not yet implemented")
    }

    override fun getMovieReviews(movieId: Int): Flow<PagingData<Review>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieCredits(movieId: Int): List<Credit> {
        TODO("Not yet implemented")
    }

    override suspend fun addMovieRating(movieId: Int, rating: Int) {
        TODO("Not yet implemented")
    }
}