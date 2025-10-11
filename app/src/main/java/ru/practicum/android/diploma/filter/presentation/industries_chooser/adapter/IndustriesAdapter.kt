package ru.practicum.android.diploma.filter.presentation.industries_chooser.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.network.domain.models.FilterIndustry

class IndustriesAdapter(defaultId: Int = -1) : RecyclerView.Adapter<IndustriesAdapter.IndustriesViewHolder>() {

    private var list: List<FilterIndustry> = mutableListOf()
    private var chosenPosition = -1
    private var chosenId = defaultId

    fun setList(list: List<FilterIndustry>) {
        this.list = list
        Log.d("TEST", list.toString())
        notifyDataSetChanged()
    }

    fun getChosenId() = chosenId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustriesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.industry_item, parent, false)
        return IndustriesViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: IndustriesViewHolder, position: Int) {
        val id = holder.bind(list[position])
        if (id == chosenId) {
            chosenPosition = holder.adapterPosition
        }
        holder.itemView.setOnClickListener {
            chosenId = id
            chosenPosition = holder.adapterPosition
            notifyDataSetChanged()
        }
        holder.radioButton.setOnClickListener {
            chosenId = id
            chosenPosition = holder.adapterPosition
            notifyDataSetChanged()
        }
        holder.radioButton.isChecked = holder.adapterPosition == chosenPosition
    }

    class IndustriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val radioButton = itemView.findViewById<RadioButton>(R.id.radio_button)
        fun bind(industry: FilterIndustry): Int {
            itemView.findViewById<TextView>(R.id.industry_name).text = industry.name
            return industry.id
        }
    }
}
