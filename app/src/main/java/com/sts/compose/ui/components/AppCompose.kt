package com.sts.compose.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sts.compose.ui.theme.AppTheme
import com.sts.compose.ui.topbar.TopBarApp
import com.sts.feature.di.viewmodel.MainViewModel
import com.sts.feature.navigation.AppNavHost
import com.sts.feature.navigation.Route
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCompose() {
    val mainViewModel = koinViewModel<MainViewModel>()
    val navController = rememberNavController()

    AppTheme {
        val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
        val topBarState = rememberSaveable { (mutableStateOf(false)) }
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        ManagerStateTopBarAndBottomBar(
            navController = navController,
            topBarState = topBarState,
            bottomBarState = bottomBarState
        )

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopBarApp(
                    navController = navController,
                    topBarState = topBarState,
                    routeName = navBackStackEntry?.destination?.route ?: ""
                )
            }
        ) { _ ->
            AppNavHost(
                modifier = Modifier,
                navController = navController
            )
        }
    }
    val isShowLoading = remember {
        mutableStateOf(false)
    }

    when (mainViewModel.isShowLoading.collectAsState().value) {
        true -> {
            if (!isShowLoading.value)
                LaunchedEffect(key1 = Unit) {
                    navController.navigate(Route.DialogLoading.route)
                    isShowLoading.value = true
                }
        }
        false -> {
            if (isShowLoading.value)
                LaunchedEffect(key1 = Unit) {
                    navController.popBackStack()
                    isShowLoading.value = false
                }
        }
        else -> {
            // Todo: no action
        }
    }

}

