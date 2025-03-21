package ru.antonlm.multimodulecourses.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import ru.antonlm.common.di.ViewModelFactoryModule
import ru.antonlm.common.di.annotations.FeatureScope
import ru.antonlm.common.di.annotations.ViewModelKey
import ru.antonlm.multimodulecourses.ui.MainActivity
import ru.antonlm.multimodulecourses.ui.MainViewModel

@FeatureScope
@Component(
    dependencies = [MainActivityComponentDeps::class],
    modules = [MainActivityModule::class]
)
internal interface MainActivityComponent {
    @Component.Builder
    interface Builder {
        fun deps(homeComponentDeps: MainActivityComponentDeps): Builder
        fun build(): MainActivityComponent
    }

    fun inject(mainActivity: MainActivity)
}

@Module(includes = [ViewModelFactoryModule::class])
internal abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainActivityViewModel(mainViewModel: MainViewModel): ViewModel
}
