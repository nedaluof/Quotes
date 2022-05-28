package com.nedaluof.quotes.ui.anime.animequotes.characterquotes

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nedaluof.domain.usecases.anime.getcharacterquotes.GetCharacterQuotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by NedaluOf on 8/12/2021.
 */
@HiltViewModel
class CharacterQuotesViewModel @Inject constructor(
    private val useCase: GetCharacterQuotesUseCase
) : BaseViewModel() {

    fun loadCharacterQuotes(
        characterName: String,
    ) = useCase.loadCharacterQuotesPaginated(characterName)
        .cachedIn(viewModelScope)

}