package com.dip.sickmovies.compose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dip.sickmovies.navigation.Screen
import com.dip.sickmovies.utils.Utils
import com.dip.sickmovies.viewmodels.MovieViewModel

@Composable
fun NavigationBar(context: Context) {
    val navController = rememberNavController()
    val title = remember {
        mutableStateOf("Popular")
    }
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = title.value) },
                    actions = {
                        IconButton(onClick = {
                            Toast.makeText(
                                context,
                                "Search",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    }
                )
            }, bottomBar = {
                val items = listOf<Screen>(
                    Screen.Popular,
                    Screen.NowPlaying,
                    Screen.TopRated
                )
                BottomNavigation(
                    backgroundColor = Color.Transparent,
                    elevation = 15.dp,
                    modifier = Modifier
                        .background(Color.White)
                        .padding(
                            start = 22.dp,
                            end = 22.dp,
                            bottom = 15.dp,
                            top = 10.dp
                        )
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute =
                        navBackStackEntry?.arguments?.getString(Utils.KEY_ROUTE)
                    items.forEach {
                        BottomNavigationItem(
                            modifier = Modifier.background(
                                color = Color.White,
                                shape = RectangleShape
                            ),
                            selectedContentColor = Color.Red,
                            unselectedContentColor = Color.Blue,
                            icon = {
                                Icon(
                                    imageVector = it.icon,
                                    contentDescription = "Profile"
                                )
                            },
                            selected = currentRoute == it.route,
                            onClick = {
                                navController.popBackStack(
                                    navController.graph.startDestinationId,
                                    false
                                )
                                if (currentRoute != it.route) {
                                    navController.navigate(it.route)
                                }

                            }
                        )
                    }
                }
            }
        ) {
            ScreenController(
                navController = navController,
                topTitleBar = title
            )
        }
    }
}

@Composable
fun ScreenController(
    navController: NavHostController,
    topTitleBar: MutableState<String>
) {

    NavHost(navController = navController, startDestination = "popular") {
        composable("popular") { backStackEntry ->
            val viewModel = hiltViewModel<MovieViewModel>()
            viewModel.popularMovieList.observe(LocalLifecycleOwner.current) {
            }
            viewModel.isLoading.observe(LocalLifecycleOwner.current) {
            }
            Popular()
            topTitleBar.value = "Popular"
        }
        composable("nowPlaying") {
            NowPlaying()
            topTitleBar.value = "Now Playing"
        }
        composable("topRated") {
            TopRated()
            topTitleBar.value = "Top Rated"
        }
    }
}

