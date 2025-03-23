package ru.antonlm.data.domain.repository

import ru.antonlm.data.domain.NetworkResult
import ru.antonlm.data.domain.models.Course

interface CoursesRepository {
    /**
     * Method returns [List] of [Course] as [NetworkResult].
     * also gets the current selected status from the database
     */
    suspend fun getCourses(): NetworkResult<List<Course>>

    /**
    * Method returns [List] of favorite from database [Course] as [NetworkResult].
    */
    suspend fun getFavoriteCourses(): NetworkResult<List<Course>>

    /**
     * Method add [Course] to favorite in database.
     */
    suspend fun addToFavorites(course: Course)

    /**
     * Method remove [Course] from database.
     */
    suspend fun removeFromFavorites(courseId: Int)
}
