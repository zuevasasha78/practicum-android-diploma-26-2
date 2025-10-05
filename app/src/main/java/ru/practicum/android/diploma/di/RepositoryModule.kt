package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.db.data.impl.VacancyRepositoryImpl
import ru.practicum.android.diploma.db.domain.VacancyRepository
import ru.practicum.android.diploma.network.data.impl.VacancyRepositoryImpl as VacancyRepositoryNetworkImpl
import ru.practicum.android.diploma.network.domain.VacancyRepository as VacancyRepositoryNetwork

val repositoryModule = module {
    single<VacancyRepository> { VacancyRepositoryImpl(get()) }
    single<VacancyRepositoryNetwork> { VacancyRepositoryNetworkImpl(get(), get()) }
}
