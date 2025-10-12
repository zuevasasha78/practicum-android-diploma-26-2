package ru.practicum.android.diploma.filter.presentation.workplace.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.PlaceItemViewBinding
import ru.practicum.android.diploma.filter.domain.WorkPlace

class WorkPlaceAdapter(private val clickListener: WorkPlaceClickListener) :
    RecyclerView.Adapter<WorkPlaceViewHolder>() {

    private var placeItems: List<WorkPlace> = mutableListOf()

    fun setItems(places: List<WorkPlace>) {
        placeItems = places
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkPlaceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PlaceItemViewBinding.inflate(layoutInflater, parent, false)
        return WorkPlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkPlaceViewHolder, position: Int) {
        placeItems.getOrNull(position)?.let { place ->
            holder.bind(place)
            holder.itemView.setOnClickListener { clickListener.onPlaceClick(place) }
        }
    }

    override fun getItemCount(): Int {
        return placeItems.size
    }

    fun interface WorkPlaceClickListener {
        fun onPlaceClick(place: WorkPlace)
    }
}
