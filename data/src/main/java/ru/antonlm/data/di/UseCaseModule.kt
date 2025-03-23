package ru.antonlm.data.di

import dagger.Module
import dagger.Provides
import ru.antonlm.data.data.local.prefs.PreferenceStorage
import ru.antonlm.data.domain.repository.CoursesRepository
import ru.antonlm.data.domain.repository.UserRepository
import ru.antonlm.data.domain.usecases.AddToFavoritesUseCase
import ru.antonlm.data.domain.usecases.AuthUseCase
import ru.antonlm.data.domain.usecases.GetAllCoursesUseCase
import ru.antonlm.data.domain.usecases.GetFavoriteCoursesUseCase
import ru.antonlm.data.domain.usecases.GetOnboardingStatusUseCase
import ru.antonlm.data.domain.usecases.GetUserStatusUseCase
import ru.antonlm.data.domain.usecases.MarkOnboardingShownUseCase
import ru.antonlm.data.domain.usecases.RemoveFromFavoritesUseCase
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

    @Provides
    @Singleton
    fun provideAuthUseCase(userRepository: UserRepository) = AuthUseCase(userRepository)

    @Provides
    @Singleton
    fun provideGetUserStatusUseCase(userRepository: UserRepository) = GetUserStatusUseCase(userRepository)

    @Provides
    @Singleton
    fun provideGetFavoriteCoursesUseCase(coursesRepository: CoursesRepository) = GetFavoriteCoursesUseCase(coursesRepository)

    @Provides
    @Singleton
    fun provideAddToFavoritesUseCase(coursesRepository: CoursesRepository) = AddToFavoritesUseCase(coursesRepository)

    @Provides
    @Singleton
    fun provideRemoveFromFavoritesUseCase(coursesRepository: CoursesRepository) = RemoveFromFavoritesUseCase(coursesRepository)
}