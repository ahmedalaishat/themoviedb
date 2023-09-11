package com.alaishat.ahmed.themoviedb.domain

import androidx.paging.PagingData
import androidx.paging.filter
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.repository.AccountRepository
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/26/2023.
 * The Movie DB Project.
 */
private const val DEBOUNCE_TIMEOUT = 300L

class GetWatchListUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    operator fun invoke(queryFlow: Flow<String>): Flow<PagingData<Movie>> {
        val pagingFlow = accountRepository.getWatchListPagingFlow()

        return queryFlow
            .debounce(DEBOUNCE_TIMEOUT)
            .flatMapLatest { query ->
                pagingFlow.mapLatest { list ->
                    list.filter { movie ->
                        movie.title.contains(query, ignoreCase = true) ||
                                movie.overview.contains(query, ignoreCase = true)
                    }
                }
            }
    }
}