package com.dip.sickmovies.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector){

    object Popular: Screen("popular", "Popular", icon = Icons.Default.Favorite)
    object TopRated: Screen("topRated", "Top Rated", icon = Icons.Default.Refresh)
    object NowPlaying: Screen("nowPlaying", "Now Playing", icon = Icons.Default.Refresh)

}
