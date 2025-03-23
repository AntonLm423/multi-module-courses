package ru.antonlm.favorite.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import ru.antonlm.common.di.ViewModelFactoryModule
import ru.antonlm.common.di.annotations.FeatureScope
import ru.antonlm.common.di.annotations.ViewModelKey
import ru.antonlm.favorite.ui.FavoriteFragment
import ru.antonlm.favorite.ui.FavoriteViewModel

@FeatureScope
@Component(
    dependencies = [FavoriteComponentDeps::class],
    modules = [FavoriteModule::class]
)
internal interface FavoriteComponent {
    @Component.Builder
    interface Builder {
        fun deps(favoriteComponentDeps: FavoriteComponentDeps): Builder
        fun build(): FavoriteComponent
    }

    fun inject(favoriteFragment: FavoriteFragment)
}

@Module(includes = [ViewModelFactoryModule::class])
internal abstract class FavoriteModule {

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindsFavoriteViewModel(favoriteViewModel: FavoriteViewModel): ViewModel
}
