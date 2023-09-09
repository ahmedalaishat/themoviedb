package com.alaishat.ahmed.themoviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.alaishat.ahmed.themoviedb.domain.model.MovieListType
import com.alaishat.ahmed.themoviedb.domain.repository.MovieListRepository
import com.alaishat.ahmed.themoviedb.ui.MovieApp
import com.alaishat.ahmed.themoviedb.ui.theme.TheMovieDBTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var movieListRepository: MovieListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            TheMovieDBTheme {
                MovieApp()
            }
        }
//        lifecycleScope.launch {
//            movieListRepository.getMovieListByType(MovieListType.UPCOMING).collect {
//                Timber.e(it.toString())
//            }
//        }
    }
}