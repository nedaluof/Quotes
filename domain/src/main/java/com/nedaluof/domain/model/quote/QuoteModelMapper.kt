package com.nedaluof.domain.model.quote

import com.nedaluof.data.datasource.remote.apiresponse.Quote
import com.nedaluof.domain.model.mapper.Mapper
import javax.inject.Inject

/**
 * Created by NedaluOf on 5/28/2022.
 */
class QuoteModelMapper @Inject constructor() : Mapper<Quote , QuoteModel> {

    override fun from(inputModel: Quote) = QuoteModel(
        inputModel._id,
        inputModel.content,
        inputModel.tags,
        inputModel.author,
        inputModel.authorSlug
    )

    override fun to(outputModel: QuoteModel) = Quote()
}