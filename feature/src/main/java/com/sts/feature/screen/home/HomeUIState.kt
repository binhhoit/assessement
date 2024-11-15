package com.sts.feature.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class HomeUIState(
    val isLoading: MutableState<Boolean> = mutableStateOf(false),
)
