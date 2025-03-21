package ru.antonlm.multimodulecourses.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.antonlm.data.domain.usecases.MarkOnboardingShownUseCase
import ru.antonlm.onboarding.di.OnboardingComponentDeps
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent: MainActivityComponentDeps, OnboardingComponentDeps {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override val markOnboardingShownUseCase: MarkOnboardingShownUseCase
}
