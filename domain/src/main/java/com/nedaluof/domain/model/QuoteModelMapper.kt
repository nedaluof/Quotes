package com.nedaluof.domain.model

import com.nedaluof.data.datasource.remote.apiresponse.Quote
import com.nedaluof.domain.model.mapper.Mapper

/**
 * Created by NedaluOf on 5/28/2022.
 */
class QuoteModelMapper : Mapper<Quote , QuoteModel> {
    override fun from(inputModel: Quote) = QuoteModel(
        inputModel._id,
        inputModel.content,
        inputModel.tags,
        inputModel.author,
        inputModel.authorSlug
    )

    override fun to(outputModel: QuoteModel) = Quote()
}