package com.nedaluof.data.di

import com.nedaluof.data.repository.app.AppRepository
import com.nedaluof.data.repository.app.AppRepositoryImpl
import com.nedaluof.data.repository.quotes.QuotesRepository
import com.nedaluof.data.repository.quotes.QuotesRepositoryImpl
import com.nedaluof.data.repository.tags.TagsRepository
import com.nedaluof.data.repository.tags.TagsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by NedaluOf on 8/13/2021.
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAppRepository(
        repositoryImpl: AppRepositoryImpl
    ): AppRepository

    @Singleton
    @Binds
    abstract fun provideTagsRepository(
        repositoryImpl: TagsRepositoryImpl
    ): TagsRepository

    @Singleton
    @Binds
    abstract fun provideQuotesRepository(
        repositoryImpl: QuotesRepositoryImpl
    ): QuotesRepository
}