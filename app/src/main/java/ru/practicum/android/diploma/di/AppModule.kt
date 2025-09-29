package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.utils.NetworkUtils

val appModule = module {
    single { NetworkUtils(get()) }
}
