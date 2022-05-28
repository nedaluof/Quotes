package com.nedaluof.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by NedaluOf on 8/13/2021.
 */
@Parcelize
data class QuoteModel(
    val id: String = "",
    val content: String = "",
    val tags: List<String> = emptyList(),
    val author: String = "",
    val authorSlug: String = ""
) : Parcelable
