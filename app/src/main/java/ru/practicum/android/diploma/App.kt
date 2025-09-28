package ru.practicum.android.diploma

import android.app.Application
import android.content.Context

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        private lateinit var appContext: App

        fun getAppContext(): Context = appContext.applicationContext
    }
}
