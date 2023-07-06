package com.andresen.library_navigation

import androidx.annotation.StringRes

sealed class Screen(val route: String, @StringRes val resourceId: Int) {

    object Chat : Screen("chat", com.andresen.library_style.R.string.chat)
    object Map : Screen("map", com.andresen.library_style.R.string.map)
    object Profile : Screen("profile", com.andresen.library_style.R.string.profile)

    object Login : Screen("login", com.andresen.library_style.R.string.login)
}