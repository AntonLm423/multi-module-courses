package ru.antonlm.main.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import kotlin.properties.Delegates

interface MainComponentDeps {

}

interface MainComponentDepsProvider {
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: MainComponentDeps

    companion object : MainComponentDepsProvider by MainComponentDepsStore
}

object MainComponentDepsStore : MainComponentDepsProvider {
    override var deps: MainComponentDeps by Delegates.notNull()
}

internal class MainComponentViewModule : ViewModel() {
    val onboardingComponent = DaggerMainComponent.builder().deps(MainComponentDepsProvider.deps).build()
}