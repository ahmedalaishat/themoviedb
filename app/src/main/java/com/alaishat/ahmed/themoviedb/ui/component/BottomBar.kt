package com.alaishat.ahmed.themoviedb.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alaishat.ahmed.themoviedb.ui.navigation.BottomBarDestination

/**
 * Created by Ahmed Al-Aishat on Jun/18/2023.
 * The Movie DB Project.
 */
@Composable
fun MovieBottomBar(
    destinations: List<BottomBarDestination>,
    selectedDestination: BottomBarDestination?,
    onNavigateToDestination: (BottomBarDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 0.dp,
    ) {
        destinations.forEach { destination ->
            NavigationBarItem(
                onClick = {
                    onNavigateToDestination(destination)
                },
                selected = destination == selectedDestination,
                icon = { Icon(painter = painterResource(id = destination.iconId), contentDescription = null) },
                label = { Text(text = stringResource(id = destination.titleId)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.background,
                ),
            )
        }
    }
}