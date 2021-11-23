package com.clocktime.severalwest.di

import android.content.Context
import android.net.ConnectivityManager
import com.clocktime.severalwest.BuildConfig
import com.clocktime.severalwest.net.watch_interception.WatchInterceptor
import com.clocktime.severalwest.net.service.InternetConnectionService
import com.clocktime.severalwest.net.service.InternetConnectionServiceImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun connectivityManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    fun internetConnectionService(connectivityManager: ConnectivityManager): InternetConnectionService =
        InternetConnectionServiceImpl(connectivityManager)

    @Provides
    @Singleton
    fun internetConnectionInterceptor(internetConnectionService: InternetConnectionService) =
        WatchInterceptor(internetConnectionService)


    @Provides
    @Singleton
    fun alarmClockClient(internetInterceptor: WatchInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.readTimeout(100,TimeUnit.SECONDS )
        builder.writeTimeout(100,TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        builder.addInterceptor(internetInterceptor)
        return builder.build()
    }
}