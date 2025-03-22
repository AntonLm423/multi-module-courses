package ru.antonlm.multimodulecourses

import android.app.Application
import android.content.Context
import ru.antonlm.auth.di.AuthComponentDepsStore
import ru.antonlm.main.di.MainComponentDeps
import ru.antonlm.main.di.MainComponentDepsStore
import ru.antonlm.multimodulecourses.di.AppComponent
import ru.antonlm.multimodulecourses.di.DaggerAppComponent
import ru.antonlm.onboarding.di.OnboardingComponentDepsStore

class App : Application() {

    val appComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.let {
            OnboardingComponentDepsStore.deps = it
            AuthComponentDepsStore.deps = it
            MainComponentDepsStore.deps = it
        }
    }
}

internal val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }
