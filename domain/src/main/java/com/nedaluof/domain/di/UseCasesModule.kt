package com.nedaluof.domain.di

import com.nedaluof.domain.usecases.app.AppUseCase
import com.nedaluof.domain.usecases.app.AppUseCaseImpl
import com.nedaluof.domain.usecases.quotes.QuotesUseCase
import com.nedaluof.domain.usecases.quotes.QuotesUseCaseImpl
import com.nedaluof.domain.usecases.quotesbyauthor.QuotesByAuthorUseCase
import com.nedaluof.domain.usecases.quotesbyauthor.QuotesByAuthorUseCaseImpl
import com.nedaluof.domain.usecases.quotesbytag.QuotesByTagUseCase
import com.nedaluof.domain.usecases.quotesbytag.QuotesByTagUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by NedaluOf on 8/13/2021.
 */
@InstallIn(ViewModelComponent::class)
@Module
abstract class UseCasesModule {

    //App use case
    @ViewModelScoped
    @Binds
    abstract fun bindAppUseCase(
        useCaseImpl: AppUseCaseImpl
    ): AppUseCase

    //Quotes use cases
    @ViewModelScoped
    @Binds
    abstract fun bindQuotesUseCase(
        useCaseImpl: QuotesUseCaseImpl
    ): QuotesUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindQuotesByTagUseCase(
        useCaseImpl: QuotesByTagUseCaseImpl
    ): QuotesByTagUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindAuthorQuotesUseCase(
        useCaseImpl: QuotesByAuthorUseCaseImpl
    ): QuotesByAuthorUseCase

}