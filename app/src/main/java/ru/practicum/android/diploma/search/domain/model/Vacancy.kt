package ru.practicum.android.diploma.search.domain.model

data class Vacancy(
    val id: String,
    val name: String,
    val employerName: String,
    val salaryDto: Salary,
    val employerLogo: String,
)
