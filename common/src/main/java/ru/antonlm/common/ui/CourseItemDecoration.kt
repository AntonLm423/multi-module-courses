package ru.antonlm.common.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.antonlm.common.extensions.dpToPx

class CourseItemDecoration(
    private val firstItemTopMarginDp: Int,
    private val normalItemTopMarginDp: Int,
    private val lastItemBottomMarginDp: Int = firstItemTopMarginDp
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val lastItemIndex = parent.layoutManager?.itemCount?.minus(1) ?: return
        val position = parent.layoutManager?.getPosition(view) ?: return

        when (position) {
            0 -> outRect.set(0, firstItemTopMarginDp.dpToPx(), 0, 0)
            lastItemIndex -> outRect.set(0, normalItemTopMarginDp.dpToPx(), 0, lastItemBottomMarginDp.dpToPx())

            else -> outRect.set(0, normalItemTopMarginDp.dpToPx(), 0, 0)
        }
    }
}
