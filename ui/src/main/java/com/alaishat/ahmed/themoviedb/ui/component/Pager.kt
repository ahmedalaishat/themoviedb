package com.alaishat.ahmed.themoviedb.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alaishat.ahmed.themoviedb.ui.extenstions.verticalNestedScroll
import kotlinx.coroutines.launch

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppHorizontalPager(
    tabs: List<String>,
    modifier: Modifier = Modifier,
    outerScrollState: ScrollState? = null,
    pageContent: @Composable PagerScope.(page: Int) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        PagerTabRow(
            selectedTabIndex = pagerState.currentPage,
            tabs = tabs,
            onTabClick = { index ->
                coroutineScope.launch {
                    pagerState.scrollToPage(index)
                }
            }
        )

        val pagerModifier = if (outerScrollState == null) Modifier
        else Modifier.verticalNestedScroll(outerScrollState)

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = pagerModifier,
            pageContent = pageContent,
        )
    }
}


@Composable
fun PagerTabRow(
    selectedTabIndex: Int,
    tabs: List<String>,
    onTabClick: (position: Int) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    edgePadding: Dp = 0.dp,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
        TabRowDefaults.Indicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
        )
    },
    divider: @Composable () -> Unit = @Composable {
        Divider(color = MaterialTheme.colorScheme.background)
    },
) {
    val coroutineScope = rememberCoroutineScope()

    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        edgePadding = edgePadding,
        indicator = indicator,
        divider = divider,
    ) {
        tabs.forEachIndexed { index, tabName ->
            PagerTab(
                text = tabName,
                selected = selectedTabIndex == index,
                onClick = { onTabClick(index) },
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Composable
fun PagerTab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    icon: @Composable (() -> Unit)? = null,
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium
            )
        },
        icon = icon,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor,
        interactionSource = interactionSource,
    )
}