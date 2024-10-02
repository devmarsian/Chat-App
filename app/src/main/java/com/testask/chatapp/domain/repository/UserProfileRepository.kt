package com.testask.chatapp.domain.repository

import com.testask.chatapp.data.api.ChatApiService
import com.testask.chatapp.domain.models.ProfileData

class UserProfileRepository(val chatApiService: ChatApiService) {
    private var cachedProfile: ProfileData? = null

    suspend fun fetchProfileFromApi(authToken: String): ProfileData {
        return chatApiService.userInfo(authToken).body()!!.profile_data
    }

    fun getZodiac(day: Int, month: Int): String {
        return when (month) {
            1 -> if (day >= 20) "Aquarius" else "Capricorn"
            2 -> if (day >= 19) "Pisces" else "Aquarius"
            3 -> if (day >= 21) "Aries" else "Pisces"
            4 -> if (day >= 20) "Taurus" else "Aries"
            5 -> if (day >= 21) "Gemini" else "Taurus"
            6 -> if (day >= 21) "Cancer" else "Gemini"
            7 -> if (day >= 23) "Leo" else "Cancer"
            8 -> if (day >= 23) "Virgo" else "Leo"
            9 -> if (day >= 23) "Libra" else "Virgo"
            10 -> if (day >= 23) "Scorpio" else "Libra"
            11 -> if (day >= 22) "Sagittarius" else "Scorpio"
            12 -> if (day >= 22) "Capricorn" else "Sagittarius"
            else -> "Error"
        }
    }
}