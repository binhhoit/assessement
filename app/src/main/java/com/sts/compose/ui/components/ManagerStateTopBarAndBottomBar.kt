package com.sts.compose.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sts.compose.ui.theme.GreenApp
import com.sts.feature.navigation.Route

@Composable
fun ManagerStateTopBarAndBottomBar(
    navController: NavController,
    bottomBarState: MutableState<Boolean>,
    topBarState: MutableState<Boolean>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val system = rememberSystemUiController()

    when (navBackStackEntry?.destination?.route) {
        Route.Home.route, ->{
            bottomBarState.value = true
            topBarState.value = true
            system.isStatusBarVisible = true
            system.setStatusBarColor(color = GreenApp)
        }
        else -> {
            if (navBackStackEntry?.destination?.route.isNullOrBlank()) {
                bottomBarState.value = false
                topBarState.value = false
            } else {
                bottomBarState.value = false
                topBarState.value = true
                system.isStatusBarVisible = true
                system.setStatusBarColor(color = Color.Black)
            }
        }
    }
}
