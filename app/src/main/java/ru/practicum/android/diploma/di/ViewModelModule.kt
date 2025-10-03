package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.search.presentation.SearchViewModel

val viewModelModule = module {

    viewModel { SearchViewModel(get()) }
}
