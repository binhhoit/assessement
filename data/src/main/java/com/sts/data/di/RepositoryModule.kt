package com.sts.data.di

import com.sts.data.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single<ArticleRepository> { ArticleRepositoryImpl(get()) }

    /**
     * single<PaymentRepository> { PaymentRepositoryImpl(get()) }
     * single<FeatureRepository> { FeatureRepositoryImpl(get()) }
     * */
}
