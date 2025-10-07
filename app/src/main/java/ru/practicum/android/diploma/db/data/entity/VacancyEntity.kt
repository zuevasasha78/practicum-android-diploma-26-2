package ru.practicum.android.diploma.db.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val vacancyId: String,
    val name: String,
    val salaryFrom: String,
    val salaryTo: String,
    val salaryCurrency: String,
    val employerName: String,
    val employerLogoUrl: String?,
    val area: String,
    val address: String?,
    val experience: String?,
    val employment: String?,
    val description: String?,
    val responsibilities: String?,
    val requirements: String?,
    val conditions: String?,
    val skills: String,
    val url: String,
    val phone: String? = null,
    val email: String? = null
)
