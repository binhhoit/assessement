package com.sts.feature.screen.home.component

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.sts.feature.R
import com.sts.feature.ext.styleTextBold
import com.sts.feature.ext.styleTextLight
import com.sts.feature.navigation.Route
import com.sts.feature.screen.home.HomeViewModel

@Composable
fun BodyHome(
    modifier: Modifier,
    viewModel: HomeViewModel,
    direction: (String) -> Unit,
) {
    Column(
        Modifier
            .padding(top = 107.dp)
            .padding(horizontal = 10.dp)
            .fillMaxSize(),
    ) {
        Text(
            text = stringResource(id = R.string.search),
            style = styleTextBold(),
        )
        ItemHomePage(stringResource(R.string.search_articles)) {
            direction.invoke(Route.Search.route)
        }
        Spacer(modifier = modifier.fillMaxHeight(0.1f))
        Text(text = stringResource(R.string.popular), style = styleTextBold())
        ItemHomePage(stringResource(R.string.most_viewed)) {
            direction.invoke(Route.Articles.route)
        }
        ItemHomePage(stringResource(R.string.most_shared)) {
            direction.invoke(Route.Articles.route)
        }
        ItemHomePage(stringResource(R.string.most_emails)) {
            direction.invoke(Route.Articles.route)
        }
        Spacer(modifier = modifier.fillMaxHeight(0.5f))
        RequestPermissionsLocation()
    }
}

@Composable
fun ItemHomePage(string: String, callback: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                callback.invoke()
            }
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = string,
            style = styleTextLight(),
        )

        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = string,
        )
    }
    Divider()
}

@Composable
fun RequestPermissionsLocation() {
    val context = LocalContext.current
    if (checkRequestPermissionLocation(context)) {
        StartLocationUpdate(context = context)
    } else {
        RequestPermissionLocation(context)
    }
}

@Composable
fun RequestPermissionLocation(context: Context) {
    val stateCheckLocation = remember { mutableStateOf(false) }
    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissionsMap ->
        val areGranted =
            permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            stateCheckLocation.value = true
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT)
                .show()
        } else {
            stateCheckLocation.value = false
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT)
                .show()
        }
    }

    if (stateCheckLocation.value) {
        StartLocationUpdate(context)
    }

    LaunchedEffect(key1 = Unit) {
        launcherMultiplePermissions.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
    }
}

@SuppressLint("MissingPermission")
@Composable
fun StartLocationUpdate(context: Context) {
    val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val location = remember {
        mutableStateOf<Location?>(null)
    }

    locationManager.requestLocationUpdates(
        LocationManager.GPS_PROVIDER,
        0,
        0f,
        { location.value = it },
        null,
    )

    location.value?.let {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = buildString {
                    append(stringResource(R.string.latitude) + " ")
                    append(it.latitude)
                    append("\n" + stringResource(R.string.longitude) + " ")
                    append(it.longitude)
                },
                style = TextStyle(),
            )
        }
    }
}

private fun checkRequestPermissionLocation(context: Context): Boolean {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return false
    }
    return true
}
