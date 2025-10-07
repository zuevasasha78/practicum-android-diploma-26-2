package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favourites.domain.FavouriteVacancyInteractor
import ru.practicum.android.diploma.favourites.domain.FavouriteVacancyInteractorImpl
import ru.practicum.android.diploma.search.domain.SearchScreenInteractor
import ru.practicum.android.diploma.search.domain.SearchScreenInteractorImpl
import ru.practicum.android.diploma.vacancy.domain.VacancyInteractor

val interactorModule = module {

    factory<SearchScreenInteractor> {
        SearchScreenInteractorImpl(get())
    }
    factory { VacancyInteractor(get(), get()) }
    factory<FavouriteVacancyInteractor> { FavouriteVacancyInteractorImpl(get()) }
}
