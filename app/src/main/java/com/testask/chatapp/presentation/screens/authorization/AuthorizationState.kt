package com.testask.chatapp.presentation.screens.authorization

import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.Locale

data class AuthorizationState(
    val selectedCountry: String = Locale.getDefault().country,
    val countryCode: String = PhoneNumberUtil.getInstance().getCountryCodeForRegion(Locale.getDefault().country).toString(),
    val phoneNumber: String = "",
    val expanded: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val verificationCode: String = "",
    val isCodeCorrect: Boolean = false,
    val isVerificationCodeVisible: Boolean = false,
    val codeDigits: List<String> = List(6) { "" },
)

