package org.yankauskas.here.presentation.list

import androidx.lifecycle.*

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = _index.map { "This is $it" }

    fun setIndex(index: Int) {
        _index.value = index
    }
}