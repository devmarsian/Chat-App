package com.testask.chatapp.data.api

import com.testask.chatapp.domain.models.AuthCode
import com.testask.chatapp.domain.models.Phone
import com.testask.chatapp.domain.models.RefreshToken
import com.testask.chatapp.domain.models.UpdatedUserInfo
import com.testask.chatapp.domain.models.User
import com.testask.chatapp.domain.models.UserCreation
import com.testask.chatapp.domain.models.UserProfile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface ChatApiService {
    @POST("send-auth-code/")
    suspend fun sendPhone(@Body request: Phone): Response<Unit>

    @POST("register/")
    suspend fun registerUser(@Body userInfo: UserCreation): Response<RefreshToken>

    @POST("check-auth-code/")
    suspend fun login(@Body request: AuthCode): Response<User>

    @GET("me/")
    suspend fun userInfo(@Header("Authorization") authHeader: String): Response<UserProfile>

    @PUT("me/")
    suspend fun updateUserInfo(@Header("Authorization") authHeader: String, newData: UpdatedUserInfo)

    @POST("refresh-token/")
    suspend fun refreshToken(@Body refreshToken: String): Response<RefreshToken>

}
