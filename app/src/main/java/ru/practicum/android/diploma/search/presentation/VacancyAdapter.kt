package ru.practicum.android.diploma.search.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.network.domain.models.Vacancy

class VacancyAdapter(private val clickListener: VacancyClickListener) : RecyclerView.Adapter<VacancyViewHolder>() {

    private var vacancyItems: List<Vacancy> = mutableListOf()

    fun setItems(vacancy: List<Vacancy>) {
        vacancyItems = vacancy
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        return VacancyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        vacancyItems.getOrNull(position)?.let { vacancy ->
            holder.bind(vacancy)
            holder.itemView.setOnClickListener { clickListener.onVacancyClick(vacancy) }
        }
    }

    override fun getItemCount(): Int {
        return vacancyItems.size
    }

    fun interface VacancyClickListener {
        fun onVacancyClick(vacancy: Vacancy)
    }
}
