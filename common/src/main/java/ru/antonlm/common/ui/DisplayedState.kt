package ru.antonlm.common.ui

import androidx.annotation.StringRes
import ru.antonlm.common.R

/**
 * Sealed interface representing different states of data display in the application UI.
 */
sealed interface DisplayedState<T : Any> {

    class Success<T : Any>(val data: T? = null) : DisplayedState<T>

    class Loading<T : Any> : DisplayedState<T>

    class Error<T : Any>(
        val displayedMessage: String? = null,
        @StringRes val displayedMessageResId: Int = R.string.error_default
    ) : DisplayedState<T>
}
