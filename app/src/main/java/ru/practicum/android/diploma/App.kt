package ru.practicum.android.diploma

import android.app.Application
import android.content.Context

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }


    // заглушка до подключения di
    companion object {
        private var appContext: App? = null

        fun getAppContext(): Context = appContext!!.applicationContext
    }
}
