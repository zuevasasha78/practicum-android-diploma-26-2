package ru.practicum.android.diploma.search.presentation.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R

class VacancyAdapterItemDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = view.context.resources.getDimensionPixelSize(R.dimen.size_46dp)
        }
    }
}
