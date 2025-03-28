package ru.antonlm.data.di

import dagger.Module
import dagger.Provides
import ru.antonlm.data.data.local.FavoriteDao
import ru.antonlm.data.data.local.prefs.PreferenceStorage
import ru.antonlm.data.data.remote.ApiService
import ru.antonlm.data.data.repository.CoursesRepositoryImpl
import ru.antonlm.data.data.repository.UserRepositoryImpl
import ru.antonlm.data.domain.repository.CoursesRepository
import ru.antonlm.data.domain.repository.UserRepository
import javax.inject.Singleton

@Module(includes = [DataModule::class])
class RepositoryModule {
    @Provides
    @Singleton
    fun provideCourseRepository(apiService: ApiService, favoriteDao: FavoriteDao): CoursesRepository {
        return CoursesRepositoryImpl(apiService = apiService, favoriteDao = favoriteDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService, preferenceStorage: PreferenceStorage): UserRepository {
        return UserRepositoryImpl(apiService = apiService, preferenceStorage = preferenceStorage)
    }
}
