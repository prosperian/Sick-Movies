package com.dip.sickmovies

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dip.sickmovies.navigation.Screen
import com.dip.sickmovies.utils.Utils.KEY_ROUTE
import com.dip.sickmovies.ui.theme.SickmoviesTheme
import com.dip.sickmovies.viewmodels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @VisibleForTesting
    val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider() {
                SickmoviesTheme {
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
                                                this@MainActivity,
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
                                        navBackStackEntry?.arguments?.getString(KEY_ROUTE)
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
            }
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
                Log.d("Main Activity", it.results.toString())
            }
            viewModel.isLoading.observe(LocalLifecycleOwner.current) {
                Log.d("Main Activity", it.toString())
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

@Preview
@Composable
fun PreviewPopular() {
    SickmoviesTheme {
    }
}

@Composable
fun Popular() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Yellow),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.padding(1.dp),
                elevation = 16.dp,
                shape = CircleShape
            ) {
                Image(
                    modifier = Modifier.padding(25.dp),
                    imageVector = Icons.Default.Person,
                    contentDescription = "Popular",
                    colorFilter = ColorFilter.lighting(
                        Color.Blue, add = Color.Blue
                    )
                )
            }
            Spacer(modifier = Modifier.padding(6.dp))
            Text(
                text = "Popular Screen",
                fontSize = 22.sp,
                color = Color.Black
            )

        }
    }
}


@Composable
fun NowPlaying() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Yellow),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.padding(1.dp),
                elevation = 16.dp,
                shape = CircleShape
            ) {
                Image(
                    modifier = Modifier.padding(25.dp),
                    imageVector = Icons.Default.Person,
                    contentDescription = "Up Coming",
                    colorFilter = ColorFilter.lighting(
                        Color.Blue, add = Color.Blue
                    )
                )
            }
            Spacer(modifier = Modifier.padding(6.dp))
            Text(
                text = "Up Coming Screen",
                fontSize = 22.sp,
                color = Color.Black
            )

        }
    }
}

@Composable
fun TopRated() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Yellow),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.padding(1.dp),
                elevation = 16.dp,
                shape = CircleShape
            ) {
                Image(
                    modifier = Modifier.padding(25.dp),
                    imageVector = Icons.Default.Person,
                    contentDescription = "Top Rated",
                    colorFilter = ColorFilter.lighting(
                        Color.Blue, add = Color.Blue
                    )
                )
            }
            Spacer(modifier = Modifier.padding(6.dp))
            Text(
                text = "Top Rated Screen",
                fontSize = 22.sp,
                color = Color.Black
            )

        }
    }
}





