package com.testask.chatapp.common.app

import android.app.Application
import com.testask.chatapp.common.di.appModule
import com.testask.chatapp.common.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ApplClass: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@ApplClass)
            modules(appModule, networkModule)
        }
    }
}