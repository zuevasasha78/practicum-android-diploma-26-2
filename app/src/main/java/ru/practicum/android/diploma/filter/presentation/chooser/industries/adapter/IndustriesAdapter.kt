package ru.practicum.android.diploma.filter.presentation.chooser.industries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.network.domain.models.FilterIndustry

class IndustriesAdapter(
    defaultId: FilterIndustry = FilterIndustry(-1, ""),
    private val listener: IndustryAdapterListener
) : RecyclerView.Adapter<IndustriesAdapter.IndustriesViewHolder>() {

    private var list: List<FilterIndustry> = mutableListOf()
    private var chosenIndustry = defaultId

    fun setList(list: List<FilterIndustry>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun getChosenIndustry() = chosenIndustry

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustriesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.industry_item, parent, false)
        return IndustriesViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: IndustriesViewHolder, position: Int) {
        val industry = holder.bind(list[position])
        holder.itemView.setOnClickListener {
            chosenIndustry = industry
            listener.onClick()
            notifyDataSetChanged()
        }
        holder.radioButton.setOnClickListener {
            chosenIndustry = industry
            listener.onClick()
            notifyDataSetChanged()
        }
        holder.radioButton.isChecked = chosenIndustry.id == industry.id
    }

    class IndustriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val radioButton = itemView.findViewById<RadioButton>(R.id.radio_button)
        fun bind(industry: FilterIndustry): FilterIndustry {
            itemView.findViewById<TextView>(R.id.industry_name).text = industry.name
            return industry
        }
    }

    fun interface IndustryAdapterListener {
        fun onClick()
    }
}
