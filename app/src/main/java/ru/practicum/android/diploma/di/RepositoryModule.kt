package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.db.data.impl.VacancyDbRepositoryImpl
import ru.practicum.android.diploma.db.domain.VacancyDbRepository
import ru.practicum.android.diploma.network.data.impl.VacancyNetworkRepositoryImpl
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository

val repositoryModule = module {
    single<VacancyDbRepository> { VacancyDbRepositoryImpl(get(), get()) }
    single<VacancyNetworkRepository> { VacancyNetworkRepositoryImpl(get(), get()) }
}
