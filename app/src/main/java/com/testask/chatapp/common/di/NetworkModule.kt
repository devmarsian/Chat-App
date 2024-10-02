package com.testask.chatapp.common.di

import com.testask.chatapp.BuildConfig
import com.testask.chatapp.data.api.ChatApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.DOMAIN_NAME)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(ChatApiService::class.java)
    }
}