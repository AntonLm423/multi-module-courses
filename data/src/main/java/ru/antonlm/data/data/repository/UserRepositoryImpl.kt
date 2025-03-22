package ru.antonlm.data.data.repository

import ru.antonlm.data.data.models.AuthCredentials
import ru.antonlm.data.data.models.AuthRequest
import ru.antonlm.common.domain.UserStatus
import ru.antonlm.data.data.prefs.PreferenceStorage
import ru.antonlm.data.data.remote.ApiService
import ru.antonlm.data.domain.NetworkResult
import ru.antonlm.data.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val preferenceStorage: PreferenceStorage
) : UserRepository {
    override suspend fun auth(userName: String, hashPassword: String): NetworkResult<AuthCredentials> {
        val body = AuthRequest(userName, hashPassword)
        return apiService.auth(body)
    }

    override fun updateUserStatus(newStatus: UserStatus) {
        preferenceStorage.user = preferenceStorage.user.copy(userStatus = newStatus)
    }

    override fun getUserStatus(): UserStatus {
        return preferenceStorage.user.userStatus
    }
}