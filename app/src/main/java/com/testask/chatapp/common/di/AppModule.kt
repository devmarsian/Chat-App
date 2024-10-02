package com.testask.chatapp.common.di

import com.testask.chatapp.presentation.screens.authorization.AuthorizationViewModel
import com.testask.chatapp.presentation.screens.chatdetail.ChatDetailViewModel
import com.testask.chatapp.presentation.screens.registration.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AuthorizationViewModel(get()) }
    viewModel { RegistrationViewModel()}
    viewModel { ChatDetailViewModel()}
}
