package ru.practicum.android.diploma.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.utils.NetworkUtils

private const val MYSHAREDPREF = "SHARED_PREF"

val appModule = module {
    single { NetworkUtils(get()) }

    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            MYSHAREDPREF,
            Context.MODE_PRIVATE,
        )
    }
}
