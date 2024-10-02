package com.testask.chatapp.common.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.testask.chatapp.presentation.screens.authorization.AuthorizationScreen
import com.testask.chatapp.presentation.screens.chatdetail.ChatDetailScreen
import com.testask.chatapp.presentation.screens.chats.ChatsScreen
import com.testask.chatapp.presentation.screens.profile.ProfileScreen
import com.testask.chatapp.presentation.screens.registration.RegistrationScreen

@Composable
fun ChatAppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry.value?.destination

    val showBottomBar = currentDestination?.route in listOf(
        BottomNavItem.Chats.route,
        BottomNavItem.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->

        NavHost(navController, startDestination = "authorization_screen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("authorization_screen") { AuthorizationScreen(navController) }
            composable("chat_screen") { ChatsScreen(navController) }
            composable("registration_screen/{phoneNumber}") { backStackEntry ->
                val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
                RegistrationScreen(phoneNumber)
            }
            composable("chat_detail_screen/{chat}") { backStackEntry ->
                val chat = backStackEntry.arguments?.getString("chat")
                ChatDetailScreen(chat!!)
            }
            composable(BottomNavItem.Chats.route) { ChatsScreen(navController) }
            composable(BottomNavItem.Profile.route) { ProfileScreen() }
        }
    }
}