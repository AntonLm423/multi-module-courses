package ru.antonlm.data.mock

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import ru.antonlm.data.R
import ru.antonlm.data.data.remote.models.AuthCredentials
import ru.antonlm.data.data.remote.models.AuthRequest
import ru.antonlm.data.data.remote.models.CourseDto
import ru.antonlm.data.data.remote.models.CoursesResponse
import ru.antonlm.data.data.remote.ApiService
import ru.antonlm.data.domain.NetworkResult
import java.io.InputStream
import java.io.InputStreamReader

class ApiServiceMock(private val context: Application, private val gson: Gson) : ApiService {

    private suspend fun randomDelay() = delay((1000L..5000L).random())

    override suspend fun getAllCourses(): NetworkResult<CoursesResponse> {
        val courses = readCoursesFromRawResource(context, gson, R.raw.mock_courses)

        return NetworkResult.Success(courses)
    }

    private fun readCoursesFromRawResource(context: Context, gson: Gson, resourceId: Int): CoursesResponse {
        val inputStream: InputStream = context.resources.openRawResource(resourceId)
        val inputStreamReader = InputStreamReader(inputStream)

        val type = object : TypeToken<CoursesResponse>() {}.type
        return gson.fromJson(inputStreamReader, type)
    }

    override suspend fun auth(request: AuthRequest): NetworkResult<AuthCredentials> {
        randomDelay()

        return NetworkResult.Success(AuthCredentials("123", "456"))
    }
}