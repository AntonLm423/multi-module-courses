package ru.antonlm.auth.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.antonlm.auth.R
import ru.antonlm.common.ui.DisplayedState
import ru.antonlm.data.domain.onFailure
import ru.antonlm.data.domain.onSuccess
import ru.antonlm.data.domain.usecases.AuthUseCase
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel() {

    private val _authResult = MutableLiveData<DisplayedState<Nothing>>()
    val authResult: LiveData<DisplayedState<Nothing>> = _authResult

    private val _isLoginButtonEnabled = MutableLiveData<Boolean>(false)
    val isLoginButtonEnabled: LiveData<Boolean> = _isLoginButtonEnabled

    fun validateInput(email: String, password: String) {
        val isEmailValid = isValidEmail(email)
        val isPasswordFilled = password.isNotBlank()
        _isLoginButtonEnabled.value = isEmailValid && isPasswordFilled
    }

    fun auth(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            if (!isValidEmail(email)) {
                _authResult.value = DisplayedState.Error(displayedMessageResId = R.string.error_incorrect_email)
                return
            }

            _authResult.value = DisplayedState.Loading()

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val hashPassword = createHashPassword(password)
                    val result = authUseCase.invoke(email, hashPassword)

                    result.onSuccess {
                        _authResult.postValue(DisplayedState.Success())
                    }.onFailure {
                        _authResult.postValue(DisplayedState.Error())
                    }
                }
            }
        } else {
            _authResult.value = DisplayedState.Error(displayedMessageResId = R.string.error_empty_fields)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
        return email.matches(emailPattern.toRegex())
    }

    // just example
    private fun createHashPassword(rawPassword: String): String {
        val messageDigest = MessageDigest.getInstance("MD5").also {
            it.update(rawPassword.toByteArray())
        }

        return BigInteger(1, messageDigest.digest()).toString(16).padStart(32, '0')
    }
}
