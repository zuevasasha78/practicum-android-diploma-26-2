package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favourites.data.VacancyDbRepositoryImpl
import ru.practicum.android.diploma.favourites.domain.db.VacancyDbRepository
import ru.practicum.android.diploma.filter.data.SharedPreferencesRepositoryImpl
import ru.practicum.android.diploma.filter.data.WorkplaceRepositoryImpl
import ru.practicum.android.diploma.filter.domain.SharedPreferencesRepository
import ru.practicum.android.diploma.filter.domain.WorkplaceRepository
import ru.practicum.android.diploma.search.data.VacancyNetworkRepositoryImpl
import ru.practicum.android.diploma.search.domain.VacancyNetworkRepository

val repositoryModule = module {
    single<VacancyDbRepository> { VacancyDbRepositoryImpl(get()) }
    single<VacancyNetworkRepository> { VacancyNetworkRepositoryImpl(get(), get()) }
    single<SharedPreferencesRepository> { SharedPreferencesRepositoryImpl(get(), get()) }
    single<WorkplaceRepository> { WorkplaceRepositoryImpl(get(), get()) }
}
