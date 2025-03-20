package ru.antonlm.multimodulecourses.di

import dagger.Module
import ru.antonlm.data.di.UseCaseModule

@Module(includes = [UseCaseModule::class])
object AppModule