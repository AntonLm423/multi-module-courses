package ru.antonlm.data.domain.usecases

import ru.antonlm.data.domain.repository.CoursesRepository
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(
    private val coursesRepository: CoursesRepository
) {
    suspend operator fun invoke(courseId: Int) {
        coursesRepository.removeFromFavorites(courseId)
    }
}
