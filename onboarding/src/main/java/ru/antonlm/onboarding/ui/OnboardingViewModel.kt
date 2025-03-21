package ru.antonlm.onboarding.ui

import androidx.lifecycle.ViewModel
import ru.antonlm.data.domain.usecases.MarkOnboardingShownUseCase
import javax.inject.Inject


class OnboardingViewModel @Inject constructor(private val markOnboardingShownUseCase: MarkOnboardingShownUseCase) : ViewModel() {

    fun markOnboardingShown() {
        markOnboardingShownUseCase.invoke()
    }
}
