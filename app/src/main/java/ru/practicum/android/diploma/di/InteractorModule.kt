package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.domain.VacancyInteractor

val interactorModule = module {
    factory { VacancyInteractor(get(), get()) }
}
