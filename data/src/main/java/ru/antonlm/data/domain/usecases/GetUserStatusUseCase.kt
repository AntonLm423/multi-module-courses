package ru.antonlm.data.domain.usecases

import ru.antonlm.common.domain.UserStatus
import ru.antonlm.data.domain.repository.UserRepository
import javax.inject.Inject

class GetUserStatusUseCase @Inject constructor(private val userRepository: UserRepository) {

    fun invoke(): UserStatus = userRepository.getUserStatus()
}
