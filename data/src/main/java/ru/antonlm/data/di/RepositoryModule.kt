package ru.antonlm.data.di

import dagger.Module
import dagger.Provides
import ru.antonlm.data.data.remote.ApiService
import ru.antonlm.data.data.repository.CoursesRepositoryImpl
import ru.antonlm.data.domain.repository.CoursesRepository
import javax.inject.Singleton

@Module(includes = [DataModule::class])
class RepositoryModule {
    @Provides
    @Singleton
    fun provideCourseRepository(apiService: ApiService): CoursesRepository {
        return CoursesRepositoryImpl(apiService = apiService)
    }
}
