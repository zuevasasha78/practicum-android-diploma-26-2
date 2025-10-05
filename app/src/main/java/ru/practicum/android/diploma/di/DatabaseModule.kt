package ru.practicum.android.diploma.di

import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.db.data.AppDatabase
import ru.practicum.android.diploma.db.data.VacancyDbConvertor

val dataBaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .build()
    }
    single { Gson() }
    single { VacancyDbConvertor(get()) }
    single { get<AppDatabase>().getVacancyDao() }
}
