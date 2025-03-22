package ru.antonlm.onboarding.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import ru.antonlm.common.di.ViewModelFactoryModule
import ru.antonlm.common.di.annotations.FeatureScope
import ru.antonlm.common.di.annotations.ViewModelKey
import ru.antonlm.onboarding.ui.OnboardingFragment
import ru.antonlm.onboarding.ui.OnboardingViewModel

@FeatureScope
@Component(
    dependencies = [OnboardingComponentDeps::class],
    modules = [OnboardingModule::class]
)
internal interface OnboardingComponent {
    @Component.Builder
    interface Builder {
        fun deps(authComponentDeps: OnboardingComponentDeps): Builder
        fun build(): OnboardingComponent
    }

    fun inject(onboardingFragment: OnboardingFragment)
}

@Module(includes = [ViewModelFactoryModule::class])
internal abstract class OnboardingModule {

    @Binds
    @IntoMap
    @ViewModelKey(OnboardingViewModel::class)
    abstract fun bindsOnboardingViewModel(onboardingViewModel: OnboardingViewModel): ViewModel
}
