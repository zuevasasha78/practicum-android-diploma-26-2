package ru.practicum.android.diploma.utils

import android.graphics.drawable.Drawable
import android.widget.TextView
import ru.practicum.android.diploma.network.data.dto.response.VacancyDetail
import ru.practicum.android.diploma.network.domain.models.Vacancy

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
}
