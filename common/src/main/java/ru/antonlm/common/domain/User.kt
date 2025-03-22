package ru.antonlm.common.domain

data class User(
    val userStatus: UserStatus
    // ... other user info
)

enum class UserStatus() {
    ANONYMOUS, AUTHORIZED
}
