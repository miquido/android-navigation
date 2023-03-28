package com.miquido.android.navigation.sample

import android.app.Application
import com.miquido.android.navigation.di.navigationModule
import com.miquido.android.navigation.sample.main.mainModule
import com.miquido.android.navigation.sample.next.nextModule
import com.miquido.android.navigation.sample.result.resultModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                navigationModule,
                mainModule,
                nextModule,
                resultModule
            )
        }
    }
}
