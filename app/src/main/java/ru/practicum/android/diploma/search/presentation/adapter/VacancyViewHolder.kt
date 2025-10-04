package ru.practicum.android.diploma.search.presentation.adapter

import android.content.Context
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemViewBinding
import ru.practicum.android.diploma.network.data.dto.response.Salary
import ru.practicum.android.diploma.network.domain.models.Vacancy
import java.util.Currency

class VacancyViewHolder(private val viewBinding: VacancyItemViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(vacancy: Vacancy) {
        viewBinding.vacancyName.text = vacancy.name
        viewBinding.employerName.text = vacancy.employerName
        viewBinding.salary.text = createSalaryString(vacancy.salary, itemView.context)

        val roundValue = itemView.context.resources.getDimensionPixelSize(R.dimen.logo_corner_radius)
        Glide.with(itemView.context)
            .load(vacancy.employerLogo)
            .placeholder(R.drawable.employer_logo_placeholder)
            .transform(
                RoundedCorners(roundValue)
            )
            .into(viewBinding.employerLogoImage)
    }

    private fun createSalaryString(salary: Salary, context: Context): String {
        val string = StringBuilder()
        val currencySymbol = Currency.getInstance(salary.currency).symbol
        if (salary.from == null && salary.to == null) {
            string.append(getString(context, R.string.salary_no_data))
        }
        if (salary.from != null) {
            string.append(getString(context, R.string.salary_from) + " ${salary.from} ")
        }
        if (salary.to != null) {
            string.append(getString(context, R.string.salary_to) + " ${salary.to} $currencySymbol")
        }
        return string.toString()
    }
}
