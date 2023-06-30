package com.andresen.trainingpartner.main.koinmodules.repository

import android.content.Context
import com.andresen.library_repositories.util.TrainingPartnerDispatchers
import com.andresen.library_repositories.util.TrainingPartnerDispatchersRegular
import com.andresen.library_repositories.util.network.ConnectionService
import com.andresen.library_repositories.util.network.ConnectionServiceImpl
import org.koin.core.module.Module
import org.koin.dsl.module


object RepositoryModule {
    fun createModules(context: Context): List<Module> {
        return listOf(
            module {
                /*single { AdDatabase.createDao(get()) }
                single<AdsLocalRepository> {
                    AdsLocalRepositoryImpl(get())
                }
                factory { RequestHelper(get()) }
                single { ApiServiceFactoryImpl(get()) }
                factory<AdsRepository> {
                    AdsRepositoryImpl(
                        (get() as ApiServiceFactoryImpl).createService(),
                        get(),
                        get(),
                        get()
                    )
                }
                single { AdsGlobalEvent() }*/
                factory<ConnectionService> { ConnectionServiceImpl(get()) }
                single<TrainingPartnerDispatchers> { TrainingPartnerDispatchersRegular }

            }
        )
    }
}


