package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favourites.presentation.FavouriteVacanciesViewModel
import ru.practicum.android.diploma.filter.presentation.industries_chooser.IndustriesChooserViewModel
import ru.practicum.android.diploma.filter.presentation.main.MainFilterViewModel
import ru.practicum.android.diploma.search.presentation.SearchViewModel
import ru.practicum.android.diploma.vacancy.presentation.VacancyViewModel

val viewModelModule = module {

    viewModel { SearchViewModel(get()) }
    viewModel { VacancyViewModel(get(), get()) }
    viewModel { FavouriteVacanciesViewModel(get()) }
    viewModel { IndustriesChooserViewModel(get()) }
    viewModel { MainFilterViewModel() }
}
