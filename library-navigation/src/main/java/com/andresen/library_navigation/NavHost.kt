package com.andresen.library_navigation

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andresen.feature_map.view.MapScreen
import com.andresen.feature_profile.login.view.LoginScreen
import com.andresen.feature_profile.profile.view.ProfileScreen
import com.andresen.library_repositories.login.firebase.GoogleAuthUiClient
import com.andresen.library_repositories.login.firebase.model.SignInState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TrainingPartnerNavHost(
    modifier: Modifier = Modifier,
    context: Context,
    scope: CoroutineScope,
    navController: NavHostController = rememberNavController(),
    googleAuthUiClient: GoogleAuthUiClient,
    state: SignInState,
    onSignInResultClick: (ActivityResult, GoogleAuthUiClient) -> Unit,
    onSignOutClick: (GoogleAuthUiClient, Context, NavHostController) -> Unit,
    resetState: () -> Unit
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if(googleAuthUiClient.getSignedInUser() != null) {
            "profile"
        } else {
            "login"
        }
    ) {
        composable("login") {
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    onSignInResultClick(result, googleAuthUiClient)
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if(state.isSignInSuccessful) {
                    Toast.makeText(
                        context,
                        "Log in successful",
                        Toast.LENGTH_LONG
                    ).show()

                    navController.navigate("profile")

                    resetState.invoke()
                }
            }

            LoginScreen(
                state = state,
                onSignInClick = {
                    scope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
        }

        composable("profile") {
            ProfileScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    onSignOutClick(googleAuthUiClient, context, navController)
                }
            )
        }

        composable("chat") {
            // todo
        }
        composable("map") {
            MapScreen(
                modifier = modifier
            )
        }
    }
}

