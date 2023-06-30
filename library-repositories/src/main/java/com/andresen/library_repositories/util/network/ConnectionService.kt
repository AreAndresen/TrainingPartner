package com.andresen.library_repositories.util.network

interface ConnectionService {
    fun isConnectedToInternet(): Boolean
}