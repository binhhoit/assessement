package com.sts.feature.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.sts.feature.screen.articels.ArticlesScreen
import com.sts.feature.screen.articels.ArticlesViewModel
import com.sts.feature.screen.home.HomeScreen
import com.sts.feature.screen.home.HomeViewModel
import com.sts.feature.screen.search.SearchScreen
import com.sts.feature.utils.Constants.KEYWORD
import com.sts.feature.utils.Constants.MODE
import com.sts.feature.utils.ModeArticle
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String = Route.Home.route
) {
        NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Route.Home.route) {
            val homeViewModel = koinViewModel<HomeViewModel>()
            HomeScreen(modifier = modifier, homeViewModel) { route ->
                navController.navigate(route)
            }
        }

        composable(Route.Search.route) {
            SearchScreen(modifier = modifier) { screen ->
                navController.navigate(screen)
            }
        }

            composable(Route.Articles.route,
                arguments = listOf(navArgument(MODE) {
                    defaultValue = ModeArticle.Viewed.name
                }, navArgument(KEYWORD) {
                    defaultValue = ""
                })) { backStackEntry ->
                val articlesViewModel = koinViewModel<ArticlesViewModel>()

                val mode = backStackEntry.arguments?.getString(MODE)
                    ?: ModeArticle.Viewed.name
                val keyword = backStackEntry.arguments?.getString(KEYWORD)
                ArticlesScreen(modifier = modifier,
                    viewModel = articlesViewModel,
                    mode = ModeArticle.valueOf(mode),
                    keyword = keyword)
            }

        dialog(route = Route.DialogLoading.route,
            dialogProperties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            )){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

    }
}