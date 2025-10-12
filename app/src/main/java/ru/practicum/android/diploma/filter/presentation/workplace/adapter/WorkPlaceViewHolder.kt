package ru.practicum.android.diploma.filter.presentation.workplace.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.PlaceItemViewBinding
import ru.practicum.android.diploma.filter.domain.WorkPlace

class WorkPlaceViewHolder(private val viewBinding: PlaceItemViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(place: WorkPlace) {
        val hasValue = place.value != null
        val iconRes: Int
        if (hasValue) {
            iconRes = R.drawable.icon_close
            viewBinding.place.isVisible = true
            viewBinding.placeLabel.text = place.name
            viewBinding.placeValue.text = place.value
        } else {
            iconRes = R.drawable.chevron
            viewBinding.placeTitle.apply {
                text = place.name
                isVisible = true
            }
        }

        viewBinding.action.setImageResource(iconRes)
    }
}
