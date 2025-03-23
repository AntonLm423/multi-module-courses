package ru.antonlm.data.domain.usecases

import ru.antonlm.data.data.local.prefs.PreferenceStorage
import javax.inject.Inject

class GetOnboardingStatusUseCase @Inject constructor(private val preferenceStorage: PreferenceStorage) {

    /**
     * @return true if onboarding was shown else false
     */
    fun invoke(): Boolean = preferenceStorage.isOnboardingShown

}
