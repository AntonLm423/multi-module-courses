package ru.antonlm.data.data.remote

import retrofit2.http.GET
import ru.antonlm.data.data.models.CourseDto
import ru.antonlm.data.domain.NetworkResult

interface ApiService {

    companion object {
        // TODO: Example url
        const val BASE_URL = "some url"
        const val CONNECTION_TIMEOUT = 20000L // 20 seconds
    }

    // TODO: Example api call
    @GET("courses/all")
    suspend fun getAllCourses(): NetworkResult<List<CourseDto>>

}
