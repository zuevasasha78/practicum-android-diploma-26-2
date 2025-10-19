package ru.practicum.android.diploma.utils

import android.content.Context
import androidx.core.content.ContextCompat
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.network.domain.models.Salary
import ru.practicum.android.diploma.network.domain.models.VacancyDetail
import java.util.Currency

class StringUtils(private val context: Context) {

    fun getSalaryString(salaryDto: Salary): String {
        val string = StringBuilder()
        if (salaryDto.from != null) {
            string.append(ContextCompat.getString(context, R.string.salary_from) + " ${salaryDto.from} ")
        }
        if (salaryDto.to != null) {
            string.append(ContextCompat.getString(context, R.string.salary_to) + " ${salaryDto.to} ")
        }
        if (salaryDto.from == null && salaryDto.to == null) {
            string.append(ContextCompat.getString(context, R.string.salary_no_data))
        } else {
            string.append(Currency.getInstance(salaryDto.currency).symbol)
        }
        return string.toString()
    }

    fun getShareString(vacancy: VacancyDetail): String {
        return buildString {
            appendLine(ContextCompat.getString(context, R.string.share_vacancy)+" ${vacancy.name}")
            appendLine(ContextCompat.getString(context, R.string.share_company)+" ${vacancy.employerName}")
            appendLine(ContextCompat.getString(context, R.string.share_salary)+" ${getSalaryString(vacancy.salary)}")
            appendLine(ContextCompat.getString(context, R.string.share_town)+" ${vacancy.area}")
            append(ContextCompat.getString(context, R.string.share_link)+" ${vacancy.url}")
        }
    }
}
