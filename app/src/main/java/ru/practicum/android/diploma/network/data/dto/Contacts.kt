package ru.practicum.android.diploma.network.data.dto

data class Contacts(
    val id: String,
    val name: String,
    val email: String,
    val phone: List<String>,
)
