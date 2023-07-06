package com.andresen.trainingpartner

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.andresen.library_repositories.login.firebase.GoogleAuthUiClient
import com.andresen.library_repositories.login.firebase.model.SignInResult
import com.andresen.library_repositories.login.firebase.model.SignInState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val mutableDeviceLocation: MutableStateFlow<LatLng?> = MutableStateFlow(null)

    @SuppressLint("MissingPermission")
    fun getDeviceLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val location = LatLng(task.result.latitude, task.result.longitude)

                    mutableDeviceLocation.value = location
                }
            }
        } catch (e: SecurityException) {
            // Show error or something
        }
    }


    fun onSignInResult(result: ActivityResult, googleAuthUiClient: GoogleAuthUiClient) {
        viewModelScope.launch {
            if (result.resultCode == ComponentActivity.RESULT_OK) {

                val signInResult = googleAuthUiClient.signInWithIntent(
                    intent = result.data ?: return@launch
                )

                _state.update {
                    it.copy(
                        isSignInSuccessful = signInResult.data != null,
                        signInError = signInResult.errorMessage
                    )
                }
            }
        }
    }

    fun onSignIn(googleAuthUiClient: GoogleAuthUiClient) {
        viewModelScope.launch {
            val signInIntentSender = googleAuthUiClient.signIn()
        }
    }

    fun onSignOut(googleAuthUiClient: GoogleAuthUiClient, context: Context, navController: NavController) {
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            Toast.makeText(
                context,
                "Signed out",
                Toast.LENGTH_LONG
            ).show()

            navController.popBackStack() // todo  navController.navigate("profile") fix state handling
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}