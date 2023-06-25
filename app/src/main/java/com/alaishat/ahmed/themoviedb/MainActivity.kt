package com.alaishat.ahmed.themoviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.alaishat.ahmed.themoviedb.network.sources.NetworkMovieListsDataSource
import com.alaishat.ahmed.themoviedb.network.sources.ktor.KtorMovieListsDataSource
import com.alaishat.ahmed.themoviedb.ui.MovieApp
import com.alaishat.ahmed.themoviedb.ui.theme.TheMovieDBTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataSource: KtorMovieListsDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDBTheme {
                MovieApp()
            }
        }
        lifecycleScope.launch {
            dataSource.getList(NetworkMovieListsDataSource.MovieList.UPCOMING)
        }
    }
}

@Serializable
data class TestRes(
    val dates: TestDates
)

@Serializable
data class TestDates(
    val maximum: String,
    val minimum: String,
)