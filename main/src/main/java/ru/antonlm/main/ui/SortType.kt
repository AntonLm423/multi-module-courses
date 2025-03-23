package ru.antonlm.main.ui

import androidx.annotation.StringRes
import ru.antonlm.main.R

enum class SortType(@StringRes val titleResId: Int) {

    DEFAULT(titleResId = R.string.sort_default),
    DATE_DESCENDING(titleResId = R.string.sort_date_descending)
}
