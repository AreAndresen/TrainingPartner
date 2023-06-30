package com.andresen.trainingpartner.main.koinmodules

import android.content.Context
import com.andresen.trainingpartner.MainViewModel
import com.andresen.trainingpartner.main.koinmodules.map.MapModule
import com.andresen.trainingpartner.main.koinmodules.repository.RepositoryModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModules {

    fun module(context: Context): List<Module> = listOf(
        createCommonModules(),
    )
        .union(RepositoryModule.createModules(context))
        .union(MapModule.createModules(context))
        .toList()

    private fun createCommonModules(): Module {
        return module {

            viewModel {
                MainViewModel()
            }

        }
    }
}