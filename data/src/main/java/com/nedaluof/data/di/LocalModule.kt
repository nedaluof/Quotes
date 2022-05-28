package com.nedaluof.data.di

import com.nedaluof.data.datasource.local.PreferencesManager
import com.nedaluof.data.datasource.local.PreferencesManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by NedaluOf on 8/10/2021.
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class LocalModule {

    @Singleton
    @Binds
    abstract fun bindPreferencesManager(
        impl: PreferencesManagerImpl
    ): PreferencesManager

}