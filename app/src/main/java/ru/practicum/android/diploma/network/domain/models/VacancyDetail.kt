package ru.practicum.android.diploma.network.domain.models

data class VacancyDetail(
    val id: String,
    val name: String,
    val salary: Salary,
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
