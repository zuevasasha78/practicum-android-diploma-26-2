package ru.practicum.android.diploma.vacancy.domain.model

data class VacancyModel(
    val id: String,
    val name: String,
    val salary: String?,
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
    val skills: List<String>,
    val url: String,
    val phone: String? = null,
    val email: String? = null
)
