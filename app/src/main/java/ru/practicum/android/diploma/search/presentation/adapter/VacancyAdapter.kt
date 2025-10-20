package ru.practicum.android.diploma.search.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyItemViewBinding
import ru.practicum.android.diploma.network.domain.models.VacancyDetail

class VacancyAdapter(private val clickListener: VacancyClickListener) : RecyclerView.Adapter<VacancyViewHolder>() {

    private var vacancyItems: List<VacancyDetail> = mutableListOf()

    fun setItems(vacancy: List<VacancyDetail>) {
        vacancyItems = vacancy
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VacancyItemViewBinding.inflate(layoutInflater, parent, false)
        return VacancyViewHolder(binding)
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
        fun onVacancyClick(vacancy: VacancyDetail)
    }
}
