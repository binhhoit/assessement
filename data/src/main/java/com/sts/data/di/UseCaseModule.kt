package com.sts.data.di

import com.sts.data.domain.article.PopularViewsUseCase
import com.sts.data.domain.article.SearchUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { PopularViewsUseCase(get(),get()) }
    factory { (searchKey:String) -> SearchUseCase(searchKey, get()) }
}
