package com.sts.feature.di.viewmodel

import androidx.lifecycle.ViewModel
import com.sts.base.util.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow

abstract class MainViewModel :ViewModel() {
    abstract val showBottomNavigation: SingleLiveEvent<Boolean>
    abstract val isShowLoading: MutableStateFlow<Boolean?>
    abstract val showPopupError: MutableStateFlow<String?>
}