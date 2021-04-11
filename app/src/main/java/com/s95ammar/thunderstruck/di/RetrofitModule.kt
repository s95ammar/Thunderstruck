package com.s95ammar.thunderstruck.di

import com.google.gson.GsonBuilder
import com.s95ammar.thunderstruck.BuildConfig
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.AccuWeatherApiConfig
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            }
            .connectTimeout(AccuWeatherApiConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(AccuWeatherApiConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(AccuWeatherApiConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder().baseUrl(BuildConfig.SERVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setPrettyPrinting().create()))
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}
