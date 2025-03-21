package ru.antonlm.onboarding.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import ru.antonlm.data.domain.usecases.MarkOnboardingShownUseCase
import kotlin.properties.Delegates

interface OnboardingComponentDeps {
    val markOnboardingShownUseCase: MarkOnboardingShownUseCase
}

interface OnboardingComponentDepsProvider {
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: OnboardingComponentDeps

    companion object : OnboardingComponentDepsProvider by OnboardingComponentDepsStore
}

object OnboardingComponentDepsStore : OnboardingComponentDepsProvider {
    override var deps: OnboardingComponentDeps by Delegates.notNull()
}

internal class OnboardingComponentViewModule : ViewModel() {
    val onboardingComponent = DaggerOnboardingComponent.builder().deps(OnboardingComponentDepsProvider.deps).build()
}