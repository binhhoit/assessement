package com.sts.compose.ui

import com.sts.base.util.SingleLiveEvent
import com.sts.feature.di.viewmodel.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModelImpl : MainViewModel() {
    override val showBottomNavigation: SingleLiveEvent<Boolean>
        get() = SingleLiveEvent()
    override val isShowLoading = MutableStateFlow<Boolean?>(null)

    override val showPopupError: MutableStateFlow<String?>
       = MutableStateFlow(null)
}