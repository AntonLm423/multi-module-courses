package ru.antonlm.data.mock

import kotlinx.coroutines.delay
import ru.antonlm.data.data.models.AuthCredentials
import ru.antonlm.data.data.models.AuthRequest
import ru.antonlm.data.data.models.CourseDto
import ru.antonlm.data.data.remote.ApiService
import ru.antonlm.data.domain.NetworkResult

class ApiServiceMock : ApiService {

    private suspend fun randomDelay() = delay((1000L..5000L).random())

    override suspend fun getAllCourses(): NetworkResult<List<CourseDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun auth(request: AuthRequest): NetworkResult<AuthCredentials> {
        randomDelay()

        return NetworkResult.Success(AuthCredentials("123", "456"))
    }
}