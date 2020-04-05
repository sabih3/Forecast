package com.sahmed.forecaster.framework.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule {
    @Provides
    @Singleton
    fun  provideContext(app: Application) = app
}