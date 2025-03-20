package ru.antonlm.data.domain.repository

import ru.antonlm.data.domain.NetworkResult
import ru.antonlm.data.domain.models.Course

interface CoursesRepository {
    /**
     * Method returns [List] of [Course] as [NetworkResult].
     */
    suspend fun getCourses(): NetworkResult<List<Course>>
}
