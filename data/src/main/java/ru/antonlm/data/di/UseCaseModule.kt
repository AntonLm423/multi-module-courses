package ru.antonlm.data.di

import dagger.Module
import dagger.Provides
import ru.antonlm.data.domain.repository.CoursesRepository
import ru.antonlm.data.domain.usecases.GetAllCoursesUseCase
import javax.inject.Singleton

@Module(includes = [RepositoryModule::class])
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetAllCoursesUseCase(coursesRepository: CoursesRepository) = GetAllCoursesUseCase(coursesRepository)
}