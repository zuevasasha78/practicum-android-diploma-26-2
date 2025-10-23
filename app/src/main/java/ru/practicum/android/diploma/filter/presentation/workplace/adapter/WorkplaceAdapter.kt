package ru.practicum.android.diploma.filter.presentation.workplace.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.PlaceItemViewBinding
import ru.practicum.android.diploma.filter.presentation.workplace.models.WorkplaceType

class WorkplaceAdapter(private val clickListener: WorkplaceClickListener) :
    RecyclerView.Adapter<WorkplaceViewHolder>() {

    private var placeItems: List<WorkplaceType> = mutableListOf()

    fun setItems(places: List<WorkplaceType>) {
        placeItems = places
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkplaceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PlaceItemViewBinding.inflate(layoutInflater, parent, false)
        return WorkplaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkplaceViewHolder, position: Int) {
        placeItems.getOrNull(position)?.let { place ->
            holder.bind(place)
            holder.itemView.setOnClickListener { clickListener.onPlaceClick(place) }
        }
    }

    override fun getItemCount(): Int {
        return placeItems.size
    }

    fun interface WorkplaceClickListener {
        fun onPlaceClick(place: WorkplaceType)
    }
}
