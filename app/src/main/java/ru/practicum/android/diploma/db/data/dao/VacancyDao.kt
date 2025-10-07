package ru.practicum.android.diploma.db.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.db.data.entity.VacancyEntity

@Dao
interface VacancyDao {

    @Insert(entity = VacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVacancy(vacancy: VacancyEntity)

    @Query("SELECT * FROM vacancy_table")
    suspend fun getVacancies(): List<VacancyEntity>

    @Query("DELETE FROM vacancy_table WHERE vacancyId = :vacancyId")
    suspend fun deleteVacancy(vacancyId: String)
}
