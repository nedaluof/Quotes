package com.nedaluof.domain.di

import com.nedaluof.data.datasource.remote.apiresponse.Quote
import com.nedaluof.data.datasource.remote.apiresponse.Tag
import com.nedaluof.domain.model.mapper.Mapper
import com.nedaluof.domain.model.quote.QuoteModel
import com.nedaluof.domain.model.quote.QuoteModelMapper
import com.nedaluof.domain.model.tag.TagModel
import com.nedaluof.domain.model.tag.TagModelMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by NedaluOf on 5/28/2022.
 */
@InstallIn(ViewModelComponent::class)
@Module
abstract class MapperModule {

  @ViewModelScoped
  @Binds
  abstract fun bindTagModelMapper(
    impl: TagModelMapper
  ): Mapper<Tag, TagModel>

  @ViewModelScoped
  @Binds
  abstract fun bindQuoteModelMapper(
    impl: QuoteModelMapper
  ): Mapper<Quote, QuoteModel>

}