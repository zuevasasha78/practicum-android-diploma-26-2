package ru.practicum.android.diploma.db.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.db.data.dao.VacancyDao
import ru.practicum.android.diploma.db.data.entity.VacancyEntity

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
