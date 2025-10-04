package ru.practicum.android.diploma.network.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.network.data.dto.response.FilterArea
import ru.practicum.android.diploma.network.data.dto.response.FilterIndustry
import ru.practicum.android.diploma.network.data.dto.response.VacancyDetail
import ru.practicum.android.diploma.network.data.dto.response.VacancyResponseDto

interface DiplomaApiService {

    @GET("areas")
    suspend fun getAreas(): List<FilterArea>

    @GET("industries")
    suspend fun getIndustries(): List<FilterIndustry>

    @GET("/vacancies/{id}")
    suspend fun getVacancy(@Path("id") id: String): VacancyDetail

    @GET("/vacancies")
    suspend fun getVacancies(@QueryMap options: Map<String, String>): VacancyResponseDto
}
