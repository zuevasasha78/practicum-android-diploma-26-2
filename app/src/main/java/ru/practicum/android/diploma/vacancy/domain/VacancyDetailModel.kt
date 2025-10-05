package ru.practicum.android.diploma.vacancy.domain

data class VacancyDetailModel(
    val id: String,
    val name: String,
    val description: String,
    val salary: String?,
    val address: String?,
    val experience: String,
    val schedule: String,
    val employment: String,
    val contacts: String?,
    val employer: String,
    val employerLogo: String?,
    val area: String,
    val skills: List<String>,
    val url: String,
    val industry: String,
    val isFavorite: Boolean = false
)
