package ru.antonlm.onboarding.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.antonlm.common.extensions.dpToPx

class TechnologyItemDecoration : RecyclerView.ItemDecoration() {

    private companion object {
        /* In dp */
        private const val OUTSIDE_ITEM_MARGIN = 16
        private const val NORMAL_ITEM_MARGIN = 4
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val lastItemIndex = parent.layoutManager?.itemCount?.minus(1) ?: return
        val position = parent.layoutManager?.getPosition(view) ?: return

        when (position) {
            0 -> outRect.set(OUTSIDE_ITEM_MARGIN.dpToPx(), 0, 0, 0)
            lastItemIndex -> outRect.set(NORMAL_ITEM_MARGIN.dpToPx(), 0, OUTSIDE_ITEM_MARGIN.dpToPx(), 0)
            else -> outRect.set(NORMAL_ITEM_MARGIN.dpToPx(), 0, 0, 0)
        }
    }

}