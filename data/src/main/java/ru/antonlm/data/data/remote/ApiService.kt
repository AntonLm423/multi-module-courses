package ru.antonlm.data.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.antonlm.data.data.remote.models.AuthCredentials
import ru.antonlm.data.data.remote.models.AuthRequest
import ru.antonlm.data.data.remote.models.CourseDto
import ru.antonlm.data.data.remote.models.CoursesResponse
import ru.antonlm.data.domain.NetworkResult

 interface ApiService {

    companion object {
        // TODO: Example url
        const val BASE_URL = "https://someurl.com"
        const val CONNECTION_TIMEOUT = 20000L // 20 seconds
    }

    // TODO: Example api call
    @GET("courses/all")
    suspend fun getAllCourses(): NetworkResult<CoursesResponse>

    // TODO: Example api call
    @POST("user/auth")
    suspend fun auth(@Body request: AuthRequest): NetworkResult<AuthCredentials>
}
