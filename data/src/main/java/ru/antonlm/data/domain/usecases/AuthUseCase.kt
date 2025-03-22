package ru.antonlm.data.domain.usecases

import ru.antonlm.data.data.models.AuthCredentials
import ru.antonlm.common.domain.UserStatus
import ru.antonlm.data.domain.NetworkResult
import ru.antonlm.data.domain.onSuccess
import ru.antonlm.data.domain.repository.UserRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun invoke(userName: String, hashPassword: String): NetworkResult<AuthCredentials> {
        return userRepository.auth(userName, hashPassword)
            .onSuccess {
                userRepository.updateUserStatus(newStatus = UserStatus.AUTHORIZED)
                // also we need to save credentials to some storage
            }
    }
}
