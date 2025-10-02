package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.domain.GetVacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.RemoveFromFavouriteInteractor
import ru.practicum.android.diploma.vacancy.domain.ShareVacancyInteractor

val interactorModule = module {
    factory { GetVacancyInteractor(get()) }
    factory { ShareVacancyInteractor() }
    factory { RemoveFromFavouriteInteractor() }
}
