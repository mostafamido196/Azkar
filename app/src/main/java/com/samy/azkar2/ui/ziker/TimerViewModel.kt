package com.samy.azkar2.ui.ziker

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class TimerViewModel @Inject constructor() :ViewModel() {

    private val _timerStateFlow = MutableStateFlow<Boolean>(false)
    val timerStateFlow: MutableStateFlow<Boolean> get() = _timerStateFlow


    fun late_second() {

        _timerStateFlow.value = false

        viewModelScope.launch(Dispatchers.IO) {
            Thread.sleep(500)
            _timerStateFlow.value = true

        }

    }

}