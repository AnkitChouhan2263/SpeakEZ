package com.example.speakez.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.speakez.presentation.home.HomeScreen
import com.example.speakez.presentation.onboarding.OnboardingScreen
import com.example.speakez.presentation.practice.PracticeScreen

@Composable
fun VerbalNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "onboarding",
        modifier = modifier
    ) {
        composable("onboarding") {
            OnboardingScreen(
                onContinue = { goal ->
                    navController.navigate("home/$goal")
                }
            )
        }

        composable(
            route = "home/{userGoal}",
            arguments = listOf(navArgument("userGoal") { type = NavType.StringType })
        ) { backStackEntry ->
            val userGoal = backStackEntry.arguments?.getString("userGoal") ?: "General"
            HomeScreen(
                userGoal = userGoal,
                onStartSession = {
                    navController.navigate("practice/$userGoal")
                },
                onViewHistory = {
                    // navController.navigate("history")
                }
            )
        }

        composable(
            route = "practice/{userGoal}",
            arguments = listOf(navArgument("userGoal") { type = NavType.StringType })
        ) {
            PracticeScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
