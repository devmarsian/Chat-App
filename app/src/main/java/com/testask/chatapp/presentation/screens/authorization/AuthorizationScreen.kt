package com.testask.chatapp.presentation.screens.authorization

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.testask.chatapp.common.navigation.NavigationEvent
import com.testask.chatapp.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthorizationScreen(navController: NavController) {
    val countryList = listOf("US", "CA", "GB", "FR", "DE", "RU")
    val viewModel = koinViewModel<AuthorizationViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val focusRequesters = List(6) { FocusRequester() }
    val navigationEvent by viewModel.navigationEvent.collectAsState()

    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            is NavigationEvent.NavigateToChat -> {
                navController.navigate("chat_screen")
            }
            is NavigationEvent.NavigateToRegistration -> {
                navController.navigate("registration_screen/${(navigationEvent as NavigationEvent.NavigateToRegistration).phoneNumber}")
            }
            null -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (!uiState.isVerificationCodeVisible) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)
                ) {
                    Box {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { viewModel.processIntent(
                                AuthorizationIntent.ToggleDropdown) }
                        ) {
                            Image(
                                painter = painterResource(id = getFlagResId(uiState.selectedCountry)),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "+${uiState.countryCode}",
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                        }

                        DropdownMenu(
                            expanded = uiState.expanded,
                            onDismissRequest = { viewModel.processIntent(AuthorizationIntent.ToggleDropdown) },
                        ) {
                            countryList.forEach { country ->
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.processIntent(AuthorizationIntent.SelectCountry(country))
                                    }, text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Image(
                                                painter = painterResource(id = getFlagResId(country)),
                                                contentDescription = null,
                                                modifier = Modifier.size(24.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(text = country)
                                        }
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = uiState.phoneNumber,
                        onValueChange = {
                            viewModel.processIntent(AuthorizationIntent.EnterPhoneNumber(it))
                        },
                        label = { Text(text = "Mobile") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = "9219999999",
                                color = Color.Gray
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.processIntent(AuthorizationIntent.GetCode) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uiState.phoneNumber.isNotBlank() && uiState.phoneNumber.length >= 10
                ) {
                    Text(text = "Send Verification Code")
                }
            } else {
                Text("Enter Verification Code:", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    uiState.codeDigits.forEachIndexed { index, digit ->
                        OutlinedTextField(
                            value = digit,
                            onValueChange = { newValue ->
                                if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                    viewModel.processIntent(AuthorizationIntent.EnterVerificationCode(newValue, index))
                                    if (newValue.isNotBlank() && index < 5) {
                                        focusRequesters[index + 1].requestFocus()
                                    }
                                }
                            },
                            modifier = Modifier
                                .height(60.dp)
                                .width(40.dp)
                                .focusRequester(focusRequesters[index]),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val verificationCode = uiState.codeDigits.joinToString("")
                        viewModel.processIntent(AuthorizationIntent.VerifyCode(verificationCode))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Verify Code")
                }
            }
        }
    }
}

fun getFlagResId(selectedCountry: String): Int {
    return when (selectedCountry) {
        "RU" -> R.drawable.flag_ru
        "US" -> R.drawable.flag_us
        "GB" -> R.drawable.flag_gb
        "FR" -> R.drawable.flag_fr
        "DE" -> R.drawable.flag_de
        "CA" -> R.drawable.flag_ca
        else -> R.drawable.ic_launcher_background
    }
}

