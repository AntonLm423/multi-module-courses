package ru.antonlm.main.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.antonlm.common.extensions.dpToPx

class MainItemDecoration : RecyclerView.ItemDecoration() {

    private companion object {
        /* In dp */
        private const val FIRST_ITEM_MARGIN = 0
        private const val NORMAL_ITEM_MARGIN = 16
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val lastItemIndex = parent.layoutManager?.itemCount?.minus(1) ?: return
        val position = parent.layoutManager?.getPosition(view) ?: return

        when (position) {
            0 -> outRect.set(0, FIRST_ITEM_MARGIN.dpToPx(), 0, NORMAL_ITEM_MARGIN)
            lastItemIndex -> outRect.set(0, NORMAL_ITEM_MARGIN.dpToPx(), 0, NORMAL_ITEM_MARGIN.dec())
            else -> outRect.set(0, NORMAL_ITEM_MARGIN.dpToPx(), 0, 0)
        }
    }

}
