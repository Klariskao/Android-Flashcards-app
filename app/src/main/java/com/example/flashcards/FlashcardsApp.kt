package com.example.flashcards

import android.app.Application
import com.example.flashcards.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FlashcardsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FlashcardsApp)
            modules(appModule)
        }
    }
}
