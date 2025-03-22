package ru.antonlm.data.domain.repository

import ru.antonlm.data.data.models.AuthCredentials
import ru.antonlm.common.domain.UserStatus
import ru.antonlm.data.domain.NetworkResult

interface UserRepository {
    /**
     * Method returns [AuthCredentials] as [NetworkResult].
     */
    suspend fun auth(userName: String, passwordHash: String): NetworkResult<AuthCredentials>

    /**
     * Method update [UserStatus] field Auth/NonAuth
     */
    fun updateUserStatus(newStatus: UserStatus)

    /**
     * Method returns actual [UserStatus]
     */
    fun getUserStatus(): UserStatus
}
