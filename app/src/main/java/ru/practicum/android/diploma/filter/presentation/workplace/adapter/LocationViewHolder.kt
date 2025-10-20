package ru.practicum.android.diploma.filter.presentation.workplace.adapter

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.PlaceItemViewBinding
import ru.practicum.android.diploma.filter.presentation.workplace.models.LocationUi

class LocationViewHolder(private val viewBinding: PlaceItemViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(locationUi: LocationUi) {
        viewBinding.action.setImageResource(R.drawable.chevron)
        viewBinding.placeTitle.apply {
            text = locationUi.name
            isVisible = true
        }
        val colorStateList = ContextCompat.getColorStateList(
            viewBinding.root.context,
            R.color.text_color
        )
        viewBinding.placeTitle.setTextColor(colorStateList)
    }
}
