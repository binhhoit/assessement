package com.sts.compose.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sts.compose.ui.MainViewModelImpl
import com.sts.feature.di.viewmodel.MainViewModel
import org.koin.dsl.module

val appModule = module {
    //Manager the same share view model
    single <MainViewModel> { MainViewModelImpl() }

    single<Gson> {
        GsonBuilder()
            // .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create()
    }
}
