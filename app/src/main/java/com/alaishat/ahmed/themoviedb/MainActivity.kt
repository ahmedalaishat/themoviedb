package com.alaishat.ahmed.themoviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import com.alaishat.ahmed.themoviedb.ui.MovieApp
import com.alaishat.ahmed.themoviedb.ui.theme.TheMovieDBTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var moviesRepository: MoviesRepository

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