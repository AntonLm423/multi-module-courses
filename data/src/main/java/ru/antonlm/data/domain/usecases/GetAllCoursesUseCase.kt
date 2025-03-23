package ru.antonlm.data.domain.usecases

import ru.antonlm.data.domain.NetworkResult
import ru.antonlm.common.domain.Course
import ru.antonlm.data.domain.repository.CoursesRepository
import javax.inject.Inject

class GetAllCoursesUseCase @Inject constructor(private val coursesRepository: CoursesRepository) {

    suspend fun invoke(): NetworkResult<List<Course>> = coursesRepository.getCourses()
}

