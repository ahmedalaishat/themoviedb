package com.alaishat.ahmed.themoviedb.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alaishat.ahmed.themoviedb.navigation.MovieNavHost
import com.alaishat.ahmed.themoviedb.navigation.BottomBarDestination
import com.alaishat.ahmed.themoviedb.ui.component.MovieBottomBar

/**
 * Created by Ahmed Al-Aishat on Jun/18/2023.
 * The Movie DB Project.
 */
@Composable
fun MovieApp() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        val navController = rememberNavController()

        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

        Scaffold(
            bottomBar = {
                MovieBottomBar(
                    destinations = remember { BottomBarDestination.values().toList() },
                    selectedDestination = BottomBarDestination.getByRoute(currentDestination),
                    onNavigateToDestination = { navController.navigate(it.route) }
                )
            }
        ) { contentPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                MovieNavHost(navController = navController)
            }
        }
    }
}