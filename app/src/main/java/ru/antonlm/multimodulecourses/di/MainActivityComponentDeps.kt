package ru.antonlm.multimodulecourses.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import ru.antonlm.data.domain.usecases.GetOnboardingStatusUseCase
import kotlin.properties.Delegates

interface MainActivityComponentDeps {
    val getOnboardingStatusUseCase: GetOnboardingStatusUseCase
}

interface MainActivityComponentDepsProvider {
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: MainActivityComponentDeps

    companion object : MainActivityComponentDepsProvider by MainActivityComponentDepsStore
}

object MainActivityComponentDepsStore : MainActivityComponentDepsProvider {
    override var deps: MainActivityComponentDeps by Delegates.notNull()
}

internal class MainActivityComponentViewModule : ViewModel() {
    val mainActivityComponent = DaggerMainActivityComponent.builder().deps(MainActivityComponentDepsProvider.deps).build()
}