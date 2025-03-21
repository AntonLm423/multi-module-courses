package ru.antonlm.multimodulecourses.ui

import androidx.lifecycle.ViewModel
import ru.antonlm.data.domain.usecases.GetOnboardingStatusUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(private val getOnboardingStatusUseCase: GetOnboardingStatusUseCase) : ViewModel() {

    fun getOnboardingStatus() = getOnboardingStatusUseCase.invoke()

}
