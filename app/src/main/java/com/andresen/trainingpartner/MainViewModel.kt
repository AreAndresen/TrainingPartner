package com.andresen.trainingpartner

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel() : ViewModel() {

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
}