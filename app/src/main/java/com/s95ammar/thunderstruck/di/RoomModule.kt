package com.s95ammar.thunderstruck.di

import android.app.Application
import androidx.room.Room
import com.s95ammar.thunderstruck.model.datasource.local.db.ThunderstruckDb
import com.s95ammar.thunderstruck.model.datasource.local.db.ThunderstruckDbConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideDatabaseInstance(application: Application): ThunderstruckDb {
        return Room.databaseBuilder(application.applicationContext, ThunderstruckDb::class.java, ThunderstruckDbConfig.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideScheduleDao(db: ThunderstruckDb) = db.forecastDao
}
