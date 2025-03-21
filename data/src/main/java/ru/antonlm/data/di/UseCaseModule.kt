package ru.antonlm.data.di

import dagger.Module
import dagger.Provides
import ru.antonlm.data.data.prefs.PreferenceStorage
import ru.antonlm.data.domain.repository.CoursesRepository
import ru.antonlm.data.domain.usecases.GetAllCoursesUseCase
import ru.antonlm.data.domain.usecases.GetOnboardingStatusUseCase
import ru.antonlm.data.domain.usecases.MarkOnboardingShownUseCase
import javax.inject.Singleton

@Module(includes = [RepositoryModule::class])
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetAllCoursesUseCase(coursesRepository: CoursesRepository) = GetAllCoursesUseCase(coursesRepository)

    @Provides
    @Singleton
    fun provideMarkOnboardingShownUseCase(preferenceStorage: PreferenceStorage) = MarkOnboardingShownUseCase(preferenceStorage)

    @Provides
    @Singleton
    fun provideGetOnboardingStatusUseCase(preferenceStorage: PreferenceStorage) = GetOnboardingStatusUseCase(preferenceStorage)
}