package com.s95ammar.thunderstruck.di

import com.s95ammar.thunderstruck.util.DefaultDispatcherProvider
import com.s95ammar.thunderstruck.util.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatchersModule {

    @Binds
    abstract fun bindDispatcherProvider(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider
}
