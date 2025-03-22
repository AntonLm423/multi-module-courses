package ru.antonlm.data.data.prefs

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.antonlm.common.domain.User
import ru.antonlm.common.domain.UserStatus
import javax.inject.Inject


class PreferenceStorage @Inject constructor(
    private val prefs: SharedPreferences,
    private val gson: Gson
) {

    companion object {
        private const val PREF_IS_ONBOARDING_SHOWN = "PREF_IS_ONBOARDING_SHOWN"
        private const val PREF_USER = "PREF_USER"
    }

    var isOnboardingShown: Boolean = false
        get() = prefs[PREF_IS_ONBOARDING_SHOWN, false] ?: false
        set(value) {
            prefs[PREF_IS_ONBOARDING_SHOWN] = value
            field = value
        }


    var user: User = User(UserStatus.ANONYMOUS)
        get() = prefs.getString(PREF_USER, null)?.let { jsonStr ->
            gson.fromJson(jsonStr, User::class.java)
        } ?: User(UserStatus.ANONYMOUS)
        set(value) {
            val jsonStr = gson.toJson(value).toString()
            prefs[PREF_USER] = jsonStr
            field = value
        }
}
