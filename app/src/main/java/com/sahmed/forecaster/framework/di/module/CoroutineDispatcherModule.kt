package com.sahmed.forecaster.framework.di.module

import com.sahmed.forecaster.framework.di.dispatcher.CoroutineDispatcherProvider
import com.sahmed.forecaster.framework.di.dispatcher.DispatcherProvider
import dagger.Binds
import dagger.Module

@Module
interface CoroutineDispatcherModule {

    @Binds
    fun bindDispatcher(dispatcherProvider: CoroutineDispatcherProvider):DispatcherProvider
}