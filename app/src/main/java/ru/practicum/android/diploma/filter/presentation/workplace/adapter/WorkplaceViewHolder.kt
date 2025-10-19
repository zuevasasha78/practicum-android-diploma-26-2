package ru.practicum.android.diploma.filter.presentation.workplace.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.PlaceItemViewBinding
import ru.practicum.android.diploma.filter.presentation.workplace.models.WorkplaceType

class WorkplaceViewHolder(private val viewBinding: PlaceItemViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(workplace: WorkplaceType) {
        val location = workplace.location
        val label = viewBinding.root.context.getString(workplace.title)
        if (location != null) {
            viewBinding.placeTitle.isVisible = false
            viewBinding.action.setImageResource(R.drawable.icon_close)
            viewBinding.place.isVisible = true
            viewBinding.placeLabel.text = label
            viewBinding.placeValue.text = location.name
        } else {
            viewBinding.place.isVisible = false
            viewBinding.action.setImageResource(R.drawable.chevron)
            viewBinding.placeTitle.apply {
                text = label
                isVisible = true
            }
        }
    }
}
