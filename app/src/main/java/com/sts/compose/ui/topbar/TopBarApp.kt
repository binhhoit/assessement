package com.sts.compose.ui.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sts.feature.ext.styleTextLight
import com.sts.feature.navigation.Route

/***
* @see<a href="https://m3.material.io/components/top-app-bar/specs">MATERIAL</a>
 ***/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarApp(
    navController: NavController,
    topBarState: MutableState<Boolean>,
    routeName: String
) {
    val route = Route.valueOf(routeName)
    val isMainScreen = listOf(
        Route.Home.route,
    ).contains(route?.route?:"")

    AnimatedVisibility(
        visible = topBarState.value,
        content = {
            Column {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White,
                    ),
                    title = {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center) {
                            Text(
                                textAlign = TextAlign.Center,
                                text = route?.run {
                                    stringResource(id = route.title)
                                }?:"",
                                modifier = Modifier,
                                style = styleTextLight(15.sp)
                            )
                        }
                    },
                    navigationIcon = {
                        if (!isMainScreen)
                            run {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = null
                                    )
                                }
                            }
                    }
                )
            }
        }

    )
}
