package ru.antonlm.data.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import javax.inject.Inject


class PreferenceStorage @Inject constructor(
    private val prefs: SharedPreferences,
    private val gson: Gson
) {

    companion object {
        private const val PREF_USER = "PREF_USER"
        private const val PREF_IS_ONBOARDING_SHOWN = "PREF_IS_ONBOARDING_SHOWN"
    }

    var isOnboardingShown: Boolean = false
        get() = prefs[PREF_IS_ONBOARDING_SHOWN, false] ?: false
        set(value) {
            prefs[PREF_IS_ONBOARDING_SHOWN] = value
            field = value
        }
}
