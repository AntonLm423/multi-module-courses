package ru.antonlm.multimodulecourses

import android.app.Application
import ru.antonlm.multimodulecourses.di.DaggerAppComponent
import ru.antonlm.multimodulecourses.di.MainActivityComponentDepsStore
import ru.antonlm.onboarding.di.OnboardingComponentDepsStore

class App : Application() {

    private val appComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.let {
            OnboardingComponentDepsStore.deps = it
            MainActivityComponentDepsStore.deps = it
        }
    }

}
