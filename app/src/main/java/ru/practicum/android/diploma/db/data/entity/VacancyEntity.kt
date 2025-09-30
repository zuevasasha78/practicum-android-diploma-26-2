package ru.practicum.android.diploma.db.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val vacancyId: Long,
    val name: String,
    val companyName: String,
    val employerLogo: String,
    val salary: String,
)
