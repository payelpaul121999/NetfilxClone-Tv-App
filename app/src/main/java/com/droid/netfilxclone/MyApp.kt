package com.droid.netfilxclone

import android.app.Application
import com.droid.netfilxclone.data.repository.NetflixRepository
import com.droid.netfilxclone.viewmodel.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)       // only log errors, not noise
            androidContext(this@MyApp)  // inject Android context
            modules(appModule)               // load DI module
        }
       /* val modules = listOf(
            appModule,dataModule
        )
        startKoin {
            androidContext(this@MyApp)
            modules(modules)
        }
        */

    }
}

val appModule = module {
    // Repository — single instance shared across the app
    single { NetflixRepository() }

    // ViewModel — new instance per screen, repository injected via get()
    viewModel { HomeViewModel(get()) }
}