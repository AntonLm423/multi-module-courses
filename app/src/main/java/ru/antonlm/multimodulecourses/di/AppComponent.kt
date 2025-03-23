package ru.antonlm.multimodulecourses.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.antonlm.auth.di.AuthComponentDeps
import ru.antonlm.data.domain.usecases.AddToFavoritesUseCase
import ru.antonlm.data.domain.usecases.AuthUseCase
import ru.antonlm.data.domain.usecases.GetAllCoursesUseCase
import ru.antonlm.data.domain.usecases.GetOnboardingStatusUseCase
import ru.antonlm.data.domain.usecases.MarkOnboardingShownUseCase
import ru.antonlm.data.domain.usecases.RemoveFromFavoritesUseCase
import ru.antonlm.main.di.MainComponentDeps
import ru.antonlm.multimodulecourses.ui.MainActivity
import ru.antonlm.onboarding.di.OnboardingComponentDeps
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent :
    OnboardingComponentDeps,
    AuthComponentDeps,
    MainComponentDeps {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)

    override val markOnboardingShownUseCase: MarkOnboardingShownUseCase

    override val authUseCase: AuthUseCase

    override val getAllCoursesUseCase: GetAllCoursesUseCase

    override val addToFavoritesUseCase: AddToFavoritesUseCase

    override val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
}
