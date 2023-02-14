package com.sts.data.di

import com.sts.data.local.SharePreferenceManager
import org.koin.dsl.module

val databaseModule = module {
    single { SharePreferenceManager.getInstance(get()) }
}
