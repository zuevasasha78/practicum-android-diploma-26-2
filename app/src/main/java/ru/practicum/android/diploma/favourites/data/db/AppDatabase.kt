package ru.practicum.android.diploma.favourites.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.favourites.data.db.dao.VacancyDao
import ru.practicum.android.diploma.favourites.data.db.entity.VacancyEntity

@Database(
    version = 1,
    entities = [
        VacancyEntity::class,
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getVacancyDao(): VacancyDao
}
