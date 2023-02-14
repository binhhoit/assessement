package com.sts.data.di

import com.sts.data.domain.article.PopularViewsUseCase
import org.koin.dsl.module

val userCaseModule = module {
    factory { PopularViewsUseCase(get(),get()) }
}
