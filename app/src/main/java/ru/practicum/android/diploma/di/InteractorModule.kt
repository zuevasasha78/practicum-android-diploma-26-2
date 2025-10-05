package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.domain.VacancyInteractor
import ru.practicum.android.diploma.search.domain.SearchScreenInteractor
import ru.practicum.android.diploma.search.domain.SearchScreenInteractorImpl

val interactorModule = module {

    factory<SearchScreenInteractor> {
        SearchScreenInteractorImpl(get())
    }
    factory { VacancyInteractor(get(), get()) }
}
