package ru.antonlm.main.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import ru.antonlm.common.di.ViewModelFactoryModule
import ru.antonlm.common.di.annotations.FeatureScope
import ru.antonlm.common.di.annotations.ViewModelKey
import ru.antonlm.main.ui.MainFragment
import ru.antonlm.main.ui.MainViewModel

@FeatureScope
@Component(
    dependencies = [MainComponentDeps::class],
    modules = [MainModule::class]
)
internal interface MainComponent {
    @Component.Builder
    interface Builder {
        fun deps(mainComponentDeps: MainComponentDeps): Builder
        fun build(): MainComponent
    }

    fun inject(mainFragment: MainFragment)
}

@Module(includes = [ViewModelFactoryModule::class])
internal abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(mainViewModel: MainViewModel): ViewModel
}
