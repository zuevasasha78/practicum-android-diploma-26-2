package ru.practicum.android.diploma.filter.presentation.workplace.adapter

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.PlaceItemViewBinding
import ru.practicum.android.diploma.filter.domain.Workplace
import ru.practicum.android.diploma.filter.domain.WorkplaceType

class WorkplaceViewHolder(private val viewBinding: PlaceItemViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(place: Workplace) {
        val hasValue = place.value != null
        val label = if (place.type == WorkplaceType.COUNTRY) {
            viewBinding.root.context.getString(R.string.country)
        } else {
            viewBinding.root.context.getString(R.string.region)
        }
        if (place.isMainSelector) {
            setValueForMainSelector(hasValue, label, place)
        } else {
            setAreaValue(place.value)
        }
    }

    private fun setAreaValue(value: String?) {
        viewBinding.action.setImageResource(R.drawable.chevron)
        value?.let {
            viewBinding.placeTitle.apply {
                text = value
                isVisible = true
            }
        }
        val colorStateList = ContextCompat.getColorStateList(
            viewBinding.root.context,
            R.color.text_color
        )
        viewBinding.placeTitle.setTextColor(colorStateList)
    }

    private fun setValueForMainSelector(hasValue: Boolean, label: String, place: Workplace) {
        if (hasValue) {
            viewBinding.placeTitle.isVisible = false

            viewBinding.action.setImageResource(R.drawable.icon_close)
            viewBinding.place.isVisible = true
            viewBinding.placeLabel.text = label
            viewBinding.placeValue.text = place.value
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
