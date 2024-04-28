package com.eduardossampaio.toprepos

import android.app.Application
import androidx.annotation.VisibleForTesting
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ThisApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@ThisApplication)
            modules(ModulesProvider.modules)
        }
    }

    @VisibleForTesting
    object ModulesProvider {
        val modules = listOf(appModule)
    }
}
