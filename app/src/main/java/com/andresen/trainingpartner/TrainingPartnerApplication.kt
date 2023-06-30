package com.andresen.trainingpartner

import android.app.Application
import com.andresen.trainingpartner.main.koinmodules.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class TrainingPartnerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TrainingPartnerApplication)
            fragmentFactory()
            modules(KoinModules.module(this@TrainingPartnerApplication))
        }

    }
}
