package com.sts.compose

import android.app.Application
import com.sts.compose.di.module.appModule
import com.sts.data.di.databaseModule
import com.sts.data.di.networkModule
import com.sts.data.di.repositoryModule
import com.sts.data.di.useCaseModule
import com.sts.feature.di.module.viewModelModule
import com.sts.feature.navigation.Route
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import timber.log.Timber

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@Application)
            androidFileProperties()
            modules(appModule)
            modules(viewModelModule)
            modules(networkModule)
            modules(repositoryModule)
            modules(useCaseModule)
            modules(databaseModule)
        }

        Route.initAppRoute()
    }
}
