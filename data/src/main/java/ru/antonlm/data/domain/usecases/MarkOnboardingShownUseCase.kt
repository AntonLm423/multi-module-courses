package ru.antonlm.data.domain.usecases

import ru.antonlm.data.data.local.prefs.PreferenceStorage
import javax.inject.Inject

class MarkOnboardingShownUseCase @Inject constructor(private val preferenceStorage: PreferenceStorage) {

    fun invoke() {
        preferenceStorage.isOnboardingShown = true
    }
}
