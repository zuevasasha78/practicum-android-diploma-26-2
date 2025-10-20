package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.db.domain.interactor.FavouriteVacancyInteractor
import ru.practicum.android.diploma.db.domain.interactor.FavouriteVacancyInteractorImpl
import ru.practicum.android.diploma.filter.domain.IndustriesInteractor
import ru.practicum.android.diploma.filter.domain.IndustriesInteractorImpl
import ru.practicum.android.diploma.filter.domain.SharedPrefInteractor
import ru.practicum.android.diploma.filter.domain.SharedPrefInteractorImpl
import ru.practicum.android.diploma.filter.domain.workplace.LocationInteractor
import ru.practicum.android.diploma.filter.domain.workplace.LocationInteractorImpl
import ru.practicum.android.diploma.filter.domain.workplace.WorkplaceInteractor
import ru.practicum.android.diploma.filter.domain.workplace.WorkplaceInteractorImpl
import ru.practicum.android.diploma.search.domain.SearchScreenInteractor
import ru.practicum.android.diploma.search.domain.SearchScreenInteractorImpl
import ru.practicum.android.diploma.utils.StringUtils
import ru.practicum.android.diploma.vacancy.domain.VacancyInteractor

val interactorModule = module {

    factory<SearchScreenInteractor> {
        SearchScreenInteractorImpl(get())
    }
    factory { VacancyInteractor(get(), get(), get()) }

    factory { StringUtils(get()) }
    factory<FavouriteVacancyInteractor> { FavouriteVacancyInteractorImpl(get()) }
    single<LocationInteractor> { LocationInteractorImpl(get()) }
    single<WorkplaceInteractor> { WorkplaceInteractorImpl(get()) }
    factory<IndustriesInteractor> { IndustriesInteractorImpl(get(), get()) }
    factory<SharedPrefInteractor> { SharedPrefInteractorImpl(get()) }
}
