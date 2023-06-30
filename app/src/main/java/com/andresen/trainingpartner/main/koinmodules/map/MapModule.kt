package com.andresen.trainingpartner.main.koinmodules.map

import android.content.Context
import com.andresen.feature_map.viewmodel.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object MapModule {
    fun createModules(context: Context): List<Module> {
        return listOf(
            module {
                viewModel {
                    MapViewModel()
                }
            }
        )
    }
}

