package com.alaishat.ahmed.themoviedb.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.ui.feature.home.navigation.homeRoute
import com.alaishat.ahmed.themoviedb.ui.feature.search.navigation.searchRoute
import com.alaishat.ahmed.themoviedb.ui.feature.watchlist.navigation.watchListRoute

/**
 * Created by Ahmed Al-Aishat on Jun/18/2023.
 * The Movie DB Project.
 */
enum class BottomBarDestination(
    @StringRes val titleId: Int,
    @DrawableRes val iconId: Int,
    val route: String,
) {
    HOME(R.string.home, R.drawable.ic_home, homeRoute),
    SEARCH(R.string.search, R.drawable.ic_search, searchRoute),
    WATCH_LIST(R.string.watch_list, R.drawable.ic_watch_list, watchListRoute);

    companion object {
        fun getByRoute(route: String?) = values().firstOrNull { it.route == route }
    }
}