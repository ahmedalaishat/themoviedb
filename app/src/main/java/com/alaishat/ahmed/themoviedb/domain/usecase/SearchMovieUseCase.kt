package com.alaishat.ahmed.themoviedb.domain.usecase

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.domain.constants.DEBOUNCE_TIMEOUT
import com.alaishat.ahmed.themoviedb.domain.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    operator fun invoke(queryFlow: Flow<String>): Flow<PagingData<MovieDomainModel>> {
        val emptyPagingData = PagingData.empty<MovieDomainModel>(
            sourceLoadStates = LoadStates(
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false),
            )
        )

        return queryFlow
            .debounce(DEBOUNCE_TIMEOUT)
            .flatMapLatest {
                if (it.isEmpty()) flowOf(emptyPagingData)
                else moviesRepository.getSearchMoviePagingFlow(it)
                    .catch { emitAll(emptyFlow()) }
            }
    }
}