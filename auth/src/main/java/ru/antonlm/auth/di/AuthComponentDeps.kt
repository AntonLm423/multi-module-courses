package ru.antonlm.auth.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import ru.antonlm.data.domain.usecases.AuthUseCase
import ru.antonlm.data.domain.usecases.MarkOnboardingShownUseCase
import kotlin.properties.Delegates

interface AuthComponentDeps {
    val authUseCase: AuthUseCase
}

interface AuthComponentDepsProvider {
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: AuthComponentDeps

    companion object : AuthComponentDepsProvider by AuthComponentDepsStore
}

object AuthComponentDepsStore : AuthComponentDepsProvider {
    override var deps: AuthComponentDeps by Delegates.notNull()
}

internal class AuthComponentViewModule : ViewModel() {
    val authComponent = DaggerAuthComponent.builder().deps(AuthComponentDepsProvider.deps).build()
}
