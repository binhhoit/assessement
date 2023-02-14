package com.sts.feature.screen.home

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel

abstract class HomeViewModel : ViewModel() {
    abstract val homeUiState: MutableState<HomeUIState>
}