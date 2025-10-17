package ru.practicum.android.diploma.filter.presentation.chooser.industries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.network.domain.models.FilterIndustry

class IndustriesAdapter(
    private var selectedIndustry: FilterIndustry?,
    private val listener: IndustryAdapterListener
) : ListAdapter<FilterIndustry, IndustriesAdapter.IndustryViewHolder>(DiffCallback) {

    private var industriesList: List<FilterIndustry> = emptyList()

    interface IndustryAdapterListener {
        fun onIndustrySelected(industry: FilterIndustry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val binding = IndustryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IndustryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val industry = getItem(position)
        holder.bind(industry, isSelected = selectedIndustry?.id == industry.id)
    }

    fun setList(newList: List<FilterIndustry>) {
        industriesList = newList
        submitList(newList)
    }

    fun getChosenIndustry(): FilterIndustry {
        return selectedIndustry ?: FilterIndustry(-1, "")
    }

    fun updateSelectedIndustry(industry: FilterIndustry?) {
        selectedIndustry = industry
        notifyDataSetChanged()
    }

    inner class IndustryViewHolder(
        private val binding: IndustryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(industry: FilterIndustry, isSelected: Boolean) {
            binding.industryName.text = industry.name
            binding.radioButton.isChecked = isSelected

            binding.root.setOnClickListener {
                selectedIndustry = industry
                notifyDataSetChanged()
                listener.onIndustrySelected(industry)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<FilterIndustry>() {
        override fun areItemsTheSame(oldItem: FilterIndustry, newItem: FilterIndustry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilterIndustry, newItem: FilterIndustry): Boolean {
            return oldItem == newItem
        }
    }
}
