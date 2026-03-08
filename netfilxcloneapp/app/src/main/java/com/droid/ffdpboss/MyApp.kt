package com.droid.ffdpboss

import android.app.Application
import com.droid.data.di.dataModule
import com.droid.ffdpboss.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val modules = listOf(
            appModule,dataModule
        )
        startKoin {
            androidContext(this@MyApp)
            modules(modules)
        }
    }
}