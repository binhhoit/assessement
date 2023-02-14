package com.sts.feature.di.module

import com.sts.feature.screen.articels.ArticlesViewModel
import com.sts.feature.screen.articels.ArticlesViewModelImpl
import com.sts.feature.screen.home.HomeViewModel
import com.sts.feature.screen.home.HomeViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<HomeViewModel> { HomeViewModelImpl() }
    viewModel<ArticlesViewModel> { ArticlesViewModelImpl(get(), get()) }
}
