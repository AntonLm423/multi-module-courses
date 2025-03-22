package ru.antonlm.onboarding.ui

import androidx.annotation.StringRes
import ru.antonlm.data.domain.models.DisplayableItem

internal data class Technology(val titleResId: Int, val angle: Angle, val bias: Bias) : DisplayableItem {

    companion object {
        fun normal(@StringRes titleResId: Int) =
            Technology(titleResId = titleResId, angle = Angle.DEFAULT, bias = Bias.NO)

        fun startToDown(@StringRes titleResId: Int) =
            Technology(titleResId = titleResId, angle = Angle.CLOCKWISE, bias = Bias.BOTTOM)

        fun startToTop(@StringRes titleResId: Int) =
            Technology(titleResId = titleResId, angle = Angle.COUNTERCLOCKWISE, bias = Bias.TOP)

        fun endToTop(@StringRes titleResId: Int) =
            Technology(titleResId = titleResId, angle = Angle.CLOCKWISE, bias = Bias.TOP)

        fun endToBottom(@StringRes titleResId: Int) =
            Technology(titleResId = titleResId, angle = Angle.CLOCKWISE, bias = Bias.BOTTOM)
    }
}

/**
 * Offset angle
 */
internal enum class Angle(val degree: Float) {
    DEFAULT(degree = 0f),
    CLOCKWISE(degree = -15f),
    COUNTERCLOCKWISE(degree = 15f)
}

/**
 * Offset relative to the row
 */
internal enum class Bias { TOP, NO, BOTTOM }