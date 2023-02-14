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

    // Control TopBar and BottomBar
    when (navBackStackEntry?.destination?.route) {
       /* Route.Welcome.route, Route.Login.route, Route.DialogLoading.route, Route.DialogError.route-> {
            // Show BottomBar and TopBar
            bottomBarState.value = false
            topBarState.value = false
            system.isStatusBarVisible = true
            system.setStatusBarColor(color = Color.White)
        }*/
        Route.Home.route,
        /*Route.Feeds.route,
        Route.Transaction.route,
        Route.MyProfile.route*/ ->{
            bottomBarState.value = true
            topBarState.value = true
            system.isStatusBarVisible = true
            system.setStatusBarColor(color = GreenApp)
        }
        /*Route.EditProfile.route -> {
            bottomBarState.value = false
            topBarState.value = false
        }*/
        else -> {
            if (navBackStackEntry?.destination?.route.isNullOrBlank()) {
                bottomBarState.value = false
                topBarState.value = false
            } else {
                // Show BottomBar and TopBar
                bottomBarState.value = false
                topBarState.value = true
                system.isStatusBarVisible = true
                system.setStatusBarColor(color = Color.Black)
            }
        }
    }
}
