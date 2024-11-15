package com.sts.base.permission

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

/***
 *@see google[https://developer.android.com/guide/topics/permissions/overview>]
 *@see github[https://google.github.io/accompanist/permissions/]
***/

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FeatureThatRequiresAudioPermission(callback: (() -> Unit)? = null) {
    // Camera permission state
    val recordAudioPermissionState = rememberPermissionState(
        android.Manifest.permission.RECORD_AUDIO
    )

    if (recordAudioPermissionState.status.isGranted) {
        Text("Microphone permission Granted")
        callback?.invoke()
    } else {
        Column {
            val textToShow = if (recordAudioPermissionState.status.shouldShowRationale) {
                // If the user has denied the permission but the rationale can be shown,
                // then gently explain why the app requires this permission
                "The Microphone is important for this app. Please grant the permission."
            } else {
                // If it's the first time the user lands on this feature, or the user
                // doesn't want to be asked again for this permission, explain that the
                // permission is required
                "Microphone permission required for this feature to be available. " +
                    "Please grant the permission"
            }
            Text(textToShow)
            Button(onClick = { recordAudioPermissionState.launchPermissionRequest() }) {
                Text("Request permission")
            }
        }
    }
}
