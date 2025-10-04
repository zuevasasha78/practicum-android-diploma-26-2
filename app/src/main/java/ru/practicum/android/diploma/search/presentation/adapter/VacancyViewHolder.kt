package ru.practicum.android.diploma.search.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemViewBinding
import ru.practicum.android.diploma.network.domain.models.Vacancy
import ru.practicum.android.diploma.utils.Utils.getSalaryString

class VacancyViewHolder(private val viewBinding: VacancyItemViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(vacancy: Vacancy) {
        viewBinding.vacancyName.text = vacancy.name
        viewBinding.employerName.text = vacancy.employerName
        viewBinding.salary.text = getSalaryString(vacancy.salary, itemView.context)

        val roundValue = itemView.context.resources.getDimensionPixelSize(R.dimen.logo_corner_radius)
        Glide.with(itemView.context)
            .load(vacancy.employerLogo)
            .placeholder(R.drawable.employer_logo_placeholder)
            .transform(
                RoundedCorners(roundValue)
            )
            .into(viewBinding.employerLogoImage)
    }
}
