package com.alaishat.ahmed.themoviedb.data.repository.fake

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.model.MovieListType
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
}