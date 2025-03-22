package ru.antonlm.multimodulecourses.ui

import androidx.lifecycle.ViewModel
import ru.antonlm.data.domain.usecases.GetOnboardingStatusUseCase
import ru.antonlm.data.domain.usecases.GetUserStatusUseCase
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val getOnboardingStatusUseCase: GetOnboardingStatusUseCase,
    private val getUserStatusUseCase: GetUserStatusUseCase
) : ViewModel() {

    fun isOnboardingShown() = getOnboardingStatusUseCase.invoke()

    fun getUserStatus() = getUserStatusUseCase.invoke()
}
