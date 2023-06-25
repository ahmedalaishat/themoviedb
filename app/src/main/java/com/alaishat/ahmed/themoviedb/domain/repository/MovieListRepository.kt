package com.alaishat.ahmed.themoviedb.domain.repository

import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.model.MovieListType
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
interface MovieListRepository : Repository {


    //AHMED_TODO: make me pager flow
    fun getMovieListByType(movieListType: MovieListType) : Flow<List<Movie>>
}