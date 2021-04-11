package com.s95ammar.thunderstruck.di

import android.content.Context
import android.content.SharedPreferences
import com.s95ammar.thunderstruck.model.datasource.local.sharedprefs.SharedPrefsManager
import com.s95ammar.thunderstruck.model.datasource.local.sharedprefs.SharedPrefsManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {
        @Provides
        fun provideSharedPreferences(@ApplicationContext applicationContext: Context): SharedPreferences {
            return applicationContext.getSharedPreferences(SharedPrefsManagerImpl.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        }
    }

    @Binds
    abstract fun bindSharedPreferencesManager(sharedPreferences: SharedPrefsManagerImpl): SharedPrefsManager
}
