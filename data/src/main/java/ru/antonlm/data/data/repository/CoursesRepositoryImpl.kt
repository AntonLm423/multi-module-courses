package ru.antonlm.data.data.repository

import ru.antonlm.data.utils.CourseDtoToCourseMapper
import ru.antonlm.data.data.remote.ApiService
import ru.antonlm.data.domain.NetworkResult
import ru.antonlm.data.domain.mapList
import ru.antonlm.data.domain.models.Course
import ru.antonlm.data.domain.repository.CoursesRepository

class CoursesRepositoryImpl(private val apiService: ApiService) : CoursesRepository {

    private val courseDtoToCourseMapper by lazy { CourseDtoToCourseMapper() }

    override suspend fun getCourses(): NetworkResult<List<Course>> {
        return apiService.getAllCourses().mapList(courseDtoToCourseMapper::map)
    }

}
