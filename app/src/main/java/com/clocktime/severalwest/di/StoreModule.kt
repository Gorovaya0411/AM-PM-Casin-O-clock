package com.clocktime.severalwest.di

import android.content.Context
import com.clocktime.severalwest.store.PreferenceStore
import com.clocktime.severalwest.store.PreferenceStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StoreModule {

    @Provides
    @Singleton
    fun providePreferenceStore(
        @ApplicationContext context: Context
    ): PreferenceStore = PreferenceStoreImpl(context)
}