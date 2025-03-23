package ru.antonlm.main.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import ru.antonlm.data.domain.usecases.AddToFavoritesUseCase
import ru.antonlm.data.domain.usecases.GetAllCoursesUseCase
import ru.antonlm.data.domain.usecases.RemoveFromFavoritesUseCase
import kotlin.properties.Delegates

interface MainComponentDeps {
    val getAllCoursesUseCase: GetAllCoursesUseCase
    val addToFavoritesUseCase: AddToFavoritesUseCase
    val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
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
    val mainComponent = DaggerMainComponent.builder().deps(MainComponentDepsProvider.deps).build()
}