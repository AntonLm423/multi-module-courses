package ru.antonlm.data.domain.usecases

import ru.antonlm.data.domain.NetworkResult
import ru.antonlm.common.domain.Course
import ru.antonlm.data.domain.repository.CoursesRepository
import javax.inject.Inject

class GetFavoriteCoursesUseCase @Inject constructor(
    private val coursesRepository: CoursesRepository
) {
    suspend operator fun invoke(): NetworkResult<List<Course>> {
        return coursesRepository.getFavoriteCourses()
    }
}
