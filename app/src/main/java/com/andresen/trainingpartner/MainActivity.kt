package com.andresen.trainingpartner

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.andresen.library_navigation.TrainingPartnerNavHost
import com.andresen.library_repositories.login.firebase.GoogleAuthUiClient
import com.andresen.library_style.theme.TrainingPartnerComposableTheme
import com.andresen.library_style.theme.TrainingPartnerTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {


    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                mainViewModel.getDeviceLocation(fusedLocationProviderClient)
            }
        }

    private fun askPermissions() = when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            this,
            ACCESS_FINE_LOCATION
        ) -> {
            mainViewModel.getDeviceLocation(fusedLocationProviderClient)
        }

        else -> {
            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        askPermissions()

        setContent {
            TrainingPartnerComposableTheme {
                val navController = rememberNavController()
                val items =  if(googleAuthUiClient.getSignedInUser() != null) {
                    listOf(
                    com.andresen.library_navigation.Screen.Chat,
                    com.andresen.library_navigation.Screen.Map,
                    com.andresen.library_navigation.Screen.Profile
                )} else {
                    listOf(
                        com.andresen.library_navigation.Screen.Login
                    )
                }

                val scaffoldState = rememberScaffoldState()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val state by mainViewModel.state.collectAsState()

                Scaffold(
                    modifier = Modifier,
                    backgroundColor = TrainingPartnerTheme.colors.medium,
                    contentColor = TrainingPartnerTheme.colors.contrastLight,
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavigation(
                            backgroundColor = TrainingPartnerTheme.colors.medium,
                            contentColor = TrainingPartnerTheme.colors.contrastLight,
                        ) {
                            items.forEach { screen ->
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            painter = when (screen.route) {
                                                "chat" -> painterResource(id = com.andresen.library_style.R.drawable.chat)
                                                "map" -> painterResource(id = com.andresen.library_style.R.drawable.map)
                                                "profile" -> painterResource(id = com.andresen.library_style.R.drawable.units)
                                                else -> painterResource(id = com.andresen.library_style.R.drawable.login_24)
                                            },
                                            contentDescription = null
                                        )
                                    },
                                    label = { Text(stringResource(screen.resourceId)) },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    TrainingPartnerNavHost(
                        modifier = Modifier.padding(innerPadding),
                        context = applicationContext,
                        navController = navController,
                        googleAuthUiClient = googleAuthUiClient,
                        state = state,
                        onSignInResultClick = { activityResult, googleAuthUiClient ->
                            mainViewModel.onSignInResult(activityResult, googleAuthUiClient)
                        },
                        onSignOutClick = { googleAuthUiClient, context, navController ->
                            mainViewModel.onSignOut(googleAuthUiClient, context, navController)
                        },
                        scope = lifecycleScope,
                        resetState = {
                            mainViewModel.resetState()
                        }
                    )
                }
            }
        }
    }
}