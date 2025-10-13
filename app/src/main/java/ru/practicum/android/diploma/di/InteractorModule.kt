package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.db.domain.interactor.FavouriteVacancyInteractor
import ru.practicum.android.diploma.db.domain.interactor.FavouriteVacancyInteractorImpl
import ru.practicum.android.diploma.filter.domain.IndustriesInteractor
import ru.practicum.android.diploma.filter.domain.IndustriesInteractorImpl
import ru.practicum.android.diploma.filter.domain.SharedPrefInteractor
import ru.practicum.android.diploma.filter.domain.SharedPrefInteractorImpl
import ru.practicum.android.diploma.filter.domain.AreaInteractor
import ru.practicum.android.diploma.filter.domain.AreaInteractorImpl
import ru.practicum.android.diploma.filter.domain.WorkplaceInteractor
import ru.practicum.android.diploma.filter.domain.WorkplaceInteractorImpl
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
    single<WorkplaceInteractor> { WorkplaceInteractorImpl() }
    single<AreaInteractor> { AreaInteractorImpl(get()) }
    factory<IndustriesInteractor> { IndustriesInteractorImpl(get()) }
    factory<SharedPrefInteractor> { SharedPrefInteractorImpl(get()) }
}
