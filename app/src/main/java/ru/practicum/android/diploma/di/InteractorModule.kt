package ru.practicum.android.diploma.di

import org.koin.dsl.module
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
}
