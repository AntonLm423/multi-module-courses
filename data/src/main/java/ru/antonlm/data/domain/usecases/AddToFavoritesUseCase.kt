package ru.antonlm.data.domain.usecases

import ru.antonlm.data.domain.models.Course
import ru.antonlm.data.domain.repository.CoursesRepository
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val coursesRepository: CoursesRepository
) {
    suspend operator fun invoke(course: Course) {
        coursesRepository.addToFavorites(course)
    }
}
