package ru.antonlm.multimodulecourses

import android.app.Application
import ru.antonlm.multimodulecourses.di.DaggerAppComponent

class App : Application() {

    private val appComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.apply {
            // TODO: Add deps
        }
    }

}
