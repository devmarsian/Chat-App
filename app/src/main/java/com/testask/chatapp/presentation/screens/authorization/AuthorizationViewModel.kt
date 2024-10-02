package com.testask.chatapp.presentation.screens.authorization

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.testask.chatapp.common.navigation.NavigationEvent
import com.testask.chatapp.data.api.ChatApiService
import com.testask.chatapp.domain.models.AuthCode
import com.testask.chatapp.domain.models.Phone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class AuthorizationViewModel(val chatApiService: ChatApiService) : ViewModel(), KoinComponent {
    private val phoneNumberUtil = PhoneNumberUtil.getInstance()

    private val _uiState = MutableStateFlow(AuthorizationState())
    val uiState: StateFlow<AuthorizationState> = _uiState

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent

    fun processIntent(intent: AuthorizationIntent) {
        when (intent) {
            is AuthorizationIntent.EnterPhoneNumber -> {
                _uiState.update { it.copy(phoneNumber = intent.phoneNumber) }
            }

            is AuthorizationIntent.SelectCountry -> {
                val countryCode = phoneNumberUtil.getCountryCodeForRegion(intent.country).toString()
                _uiState.update {
                    it.copy(
                        selectedCountry = intent.country,
                        countryCode = countryCode,
                        expanded = false
                    )
                }
            }

            is AuthorizationIntent.ToggleDropdown -> {
                _uiState.update { it.copy(expanded = !it.expanded) }
            }

            is AuthorizationIntent.EnterVerificationCode -> {
                _uiState.update { currentState ->
                    val updatedDigits = currentState.codeDigits.toMutableList()
                    updatedDigits[intent.index] = intent.code
                    currentState.copy(codeDigits = updatedDigits)
                }
            }

            is AuthorizationIntent.GetCode -> {
                val isValid = validatePhoneNumber(_uiState.value.phoneNumber)

                _uiState.update { currentState ->
                    if (isValid) {
                        currentState.copy(
                            isLoading = false,
                            isVerificationCodeVisible = true
                        )
                    } else {
                        currentState.copy(
                            isLoading = false,
                            errorMessage = "Invalid phone number"
                        )
                    }
                }

                viewModelScope.launch {
                    try {
                        val fullPhoneNumber =
                            "+${_uiState.value.countryCode}${_uiState.value.phoneNumber}"
                        val response = chatApiService.sendPhone(
                            Phone(
                                phone = fullPhoneNumber
                            )
                        )
                        if (response.isSuccessful) {
                            withContext(Dispatchers.Main){
                                _uiState.update {
                                    it.copy(
                                        isLoading = false
                                    )
                                }
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                        }
                    } catch (e: Exception) {
                        Log.e("SENDINGPHONE", "Exception: ${e.message}")
                    } finally {
                        _uiState.update { it.copy(isLoading = false) }
                    }
                }
            }

            is AuthorizationIntent.VerifyCode -> {

                val fullPhoneNumber =
                    "+${_uiState.value.countryCode}${_uiState.value.phoneNumber}"

                _uiState.update { currentState ->
                    currentState.copy(isLoading = true, errorMessage = null)
                }

                viewModelScope.launch {
                    try {
                        val authCode =  AuthCode(
                            phone = fullPhoneNumber,
                            code = intent.code
                        )
                        val response = chatApiService.login(
                            authCode
                        )

                        if (response.isSuccessful) {
                            val respCheck = response.body()
                            respCheck?.let {
                                if (it.is_user_exists) {
                                    _navigationEvent.value = NavigationEvent.NavigateToChat
                                } else {
                                    _navigationEvent.value =
                                        NavigationEvent.NavigateToRegistration(fullPhoneNumber)
                                }
                            }
                        } else {
                            val errorMessage = response.message()
                            _uiState.update { it.copy(isLoading = false, errorMessage = errorMessage) }
                            Log.d("VERIFY_CODE", "Verification failed: $errorMessage")
                        }
                    } catch (e: Exception) {
                        _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                        Log.e("VERIFY_CODE", "Error: ${e.message}")
                    }
                }
            }
        }
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.isNotBlank() && phoneNumber.length >= 10
    }
}