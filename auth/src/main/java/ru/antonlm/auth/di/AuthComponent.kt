package ru.antonlm.auth.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import ru.antonlm.auth.ui.AuthFragment
import ru.antonlm.auth.ui.AuthViewModel
import ru.antonlm.common.di.ViewModelFactoryModule
import ru.antonlm.common.di.annotations.FeatureScope
import ru.antonlm.common.di.annotations.ViewModelKey

@FeatureScope
@Component(
    dependencies = [AuthComponentDeps::class],
    modules = [AuthModule::class]
)
internal interface AuthComponent {
    @Component.Builder
    interface Builder {
        fun deps(authComponentDeps: AuthComponentDeps): Builder
        fun build(): AuthComponent
    }

    fun inject(authFragment: AuthFragment)
}

@Module(includes = [ViewModelFactoryModule::class])
internal abstract class AuthModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindsAuthViewModel(authViewModel: AuthViewModel): ViewModel
}
