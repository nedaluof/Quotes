package com.nedaluof.quotes.ui.motivational

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MotivationalViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is motivational quotes Fragment"
    }
    val text: LiveData<String> = _text
}