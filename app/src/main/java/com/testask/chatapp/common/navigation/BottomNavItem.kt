package com.testask.chatapp.common.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Chats : BottomNavItem("chat_screen", Icons.Default.Email, "Chats")
    object Profile : BottomNavItem("profile_screen", Icons.Default.Person, "Profile")
}
