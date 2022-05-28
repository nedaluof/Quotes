package com.nedaluof.quotes.ui.anime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nedaluof.domain.usecases.anime.GetAllAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject
/**
 * Created by NedaluOf on 8/10/2021.
 */
@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val useCase: GetAllAnimeUseCase
) : BaseViewModel() {

    val animeNames = MutableLiveData<List<String>>()
    val loading = MutableLiveData(true)

    private fun loadAllAvailableAnimeNames() {
        useCase.invoke(viewModelScope, null,
            { names ->
                loading.value = false
                animeNames.postValue(names)
            },
            { error ->
                loading.value = false
                Timber.e("Anime View Model ERROR : \n $error ")
            })
    }

    init {
        loadAllAvailableAnimeNames()
    }
}