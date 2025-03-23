package ru.antonlm.favorite.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import ru.antonlm.data.domain.usecases.AddToFavoritesUseCase
import ru.antonlm.data.domain.usecases.GetFavoriteCoursesUseCase
import ru.antonlm.data.domain.usecases.RemoveFromFavoritesUseCase
import kotlin.properties.Delegates

interface FavoriteComponentDeps {
    val getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase
    val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
}

interface FavoriteComponentDepsProvider {
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: FavoriteComponentDeps

    companion object : FavoriteComponentDepsProvider by FavoriteComponentDepsStore
}

object FavoriteComponentDepsStore : FavoriteComponentDepsProvider {
    override var deps: FavoriteComponentDeps by Delegates.notNull()
}

internal class FavoriteComponentViewModule : ViewModel() {
    val favoriteComponent = DaggerFavoriteComponent.builder().deps(FavoriteComponentDepsProvider.deps).build()
}
