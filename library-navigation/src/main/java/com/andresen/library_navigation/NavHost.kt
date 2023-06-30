package com.andresen.library_navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andresen.feature_map.view.MapScreen

@Composable
fun TrainingPartnerNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "map",
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("chat") {
            // todo
        }
        composable("map") {
            MapScreen(
                modifier = modifier
            )
        }
        composable("profile") {
            // todo
        }
    }
}