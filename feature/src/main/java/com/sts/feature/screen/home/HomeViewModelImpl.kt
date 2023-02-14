package com.sts.feature.screen.home

import androidx.compose.runtime.mutableStateOf

class HomeViewModelImpl : HomeViewModel() {
    override val homeUiState = mutableStateOf(HomeUIState())
}
