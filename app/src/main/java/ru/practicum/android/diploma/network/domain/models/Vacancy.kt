package ru.practicum.android.diploma.network.domain.models

import ru.practicum.android.diploma.network.data.dto.response.Salary

data class Vacancy(
    val id: String,
    val name: String,
    val employerName: String,
    val salary: Salary,
    val employerLogo: String,
)
