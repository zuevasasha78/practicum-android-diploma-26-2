package ru.practicum.android.diploma.filter.presentation.workplace.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.PlaceItemViewBinding
import ru.practicum.android.diploma.filter.presentation.workplace.models.LocationUi

class LocationAdapter(private val clickListener: LocationClickListener) :
    RecyclerView.Adapter<LocationViewHolder>() {

    private var placeItems: List<LocationUi> = mutableListOf()

    fun setItems(places: List<LocationUi>) {
        placeItems = places
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PlaceItemViewBinding.inflate(layoutInflater, parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        placeItems.getOrNull(position)?.let { place ->
            holder.bind(place)
            holder.itemView.setOnClickListener { clickListener.onPlaceClick(place) }
        }
    }

    override fun getItemCount(): Int {
        return placeItems.size
    }

    fun interface LocationClickListener {
        fun onPlaceClick(place: LocationUi)
    }
}
