package com.nedaluof.quotes.ui.anime.animequotes

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nedaluof.domain.usecases.anime.getanimequotes.GetAnimeQuotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by NedaluOf on 8/11/2021.
 */
@HiltViewModel
class AnimeQuotesViewModel @Inject constructor(
    private val useCase: GetAnimeQuotesUseCase
) : BaseViewModel() {

    fun loadAnimeQuotes(
        animeName: String,
    ) = useCase.loadAnimeQuotesPaginated(animeName)
        .cachedIn(viewModelScope)

}