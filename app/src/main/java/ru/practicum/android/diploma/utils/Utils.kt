package ru.practicum.android.diploma.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.core.content.ContextCompat
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.dto.requests.VacanciesFilterDto
import ru.practicum.android.diploma.network.data.dto.response.Salary
import ru.practicum.android.diploma.network.data.dto.response.VacancyDetail
import ru.practicum.android.diploma.network.data.dto.response.VacancyResponseDto
import ru.practicum.android.diploma.network.domain.models.Vacancy
import ru.practicum.android.diploma.network.domain.models.VacancyResponse
import ru.practicum.android.diploma.network.domain.models.requests.VacanciesFilter
import java.util.Currency

object Utils {

    fun TextView.setImageTop(drawable: Drawable?) {
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null)
    }

    fun VacancyDetail.map(): Vacancy {
        return Vacancy(
            this.id,
            this.name,
            this.employer.name,
            this.salary,
            this.employer.logo
        )
    }

    fun VacancyResponseDto.map(): VacancyResponse {
        return VacancyResponse(
            this.found,
            this.pages,
            this.page,
            this.items.map { it.map() }
        )
    }

    fun ApiResult<VacancyResponseDto>.map(): ApiResult<VacancyResponse> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.map())
            is ApiResult.Error -> ApiResult.Error(code)
            is ApiResult.NoInternetConnection -> ApiResult.NoInternetConnection
        }
    }

    fun VacanciesFilter.map(): VacanciesFilterDto {
        return VacanciesFilterDto(
            this.area,
            this.industry,
            this.text,
            this.salary,
            this.page,
            this.onlyWithSalary
        )
    }

    fun getSalaryString(salary: Salary, context: Context): String {
        val string = StringBuilder()
        val currencySymbol = Currency.getInstance(salary.currency).symbol
        if (salary.from == null && salary.to == null) {
            string.append(ContextCompat.getString(context, R.string.salary_no_data))
        }
        if (salary.from != null) {
            string.append(ContextCompat.getString(context, R.string.salary_from) + " ${salary.from} ")
        }
        if (salary.to != null) {
            string.append(ContextCompat.getString(context, R.string.salary_to) + " ${salary.to} $currencySymbol")
        }
        return string.toString()
    }
}
