package ru.antonlm.data.data.repository

import ru.antonlm.data.data.local.FavoriteDao
import ru.antonlm.data.data.remote.ApiService
import ru.antonlm.data.domain.NetworkResult
import ru.antonlm.data.domain.map
import ru.antonlm.data.domain.mapList
import ru.antonlm.common.domain.Course
import ru.antonlm.data.domain.repository.CoursesRepository
import ru.antonlm.data.utils.CourseDaoToCourseMapper
import ru.antonlm.data.utils.CourseDtoToCourseMapper
import ru.antonlm.data.utils.CourseToCourseDboMapper
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao
) : CoursesRepository {

    private val courseDtoToCourseMapper by lazy { CourseDtoToCourseMapper() }
    private val courseDaoToCourseMapper by lazy { CourseDaoToCourseMapper() }
    private val courseToCourseDboMapper by lazy { CourseToCourseDboMapper() }


    override suspend fun getCourses(): NetworkResult<List<Course>> {
        val coursesResult = apiService.getAllCourses().map { it.courses }.mapList(courseDtoToCourseMapper::map)

        return when (coursesResult) {
            is NetworkResult.Success -> {
                val coursesWithLikeStatus = coursesResult.data.map { course ->
                    val isFavorite = favoriteDao.isFavorite(course.id)
                    course.copy(hasLike = isFavorite)
                }
                NetworkResult.Success(coursesWithLikeStatus)
            }
            is NetworkResult.Error -> coursesResult
            is NetworkResult.Exception -> coursesResult
        }
    }

    override suspend fun getFavoriteCourses(): NetworkResult<List<Course>> {
        return NetworkResult.Success(favoriteDao.getAll().map { courseDaoToCourseMapper.map(it) })
    }

    override suspend fun addToFavorites(course: Course) {
        val courseEntity = courseToCourseDboMapper.map(course.copy(hasLike = true))
        favoriteDao.addToFavorites(courseEntity)
    }

    override suspend fun removeFromFavorites(courseId: Int) {
        favoriteDao.removeFromFavorites(courseId)
    }
}
