package com.samy.azkar2.ui.ziker

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.samy.azkar2.pojo.Hadith
import com.samy.azkar2.pojo.Ziker
import kotlinx.coroutines.launch

@HiltViewModel
class ZikerViewModel @Inject constructor(private val azkar: List<Ziker>) : ViewModel() {

    private val _timerStateFlow = MutableStateFlow<Boolean>(false)
    val timerStateFlow: MutableStateFlow<Boolean> get() = _timerStateFlow


    private lateinit var _ziker: Ziker
    fun getZikerList(): List<Hadith> {
        return _ziker.arr
    }
    fun getZikerSize(): Int {
        return _ziker.arr.size
    }
    fun getZikerItemState(index:Int): Int {
        return _ziker.arr[index].state
    }
    fun setZikerItemState(index:Int,state:Int) {
        _ziker.arr[index].state = state
    }
    fun increaseZikerItemState(index:Int) {
        _ziker.arr[index].state += 1
    }
    fun getZikerMaxItemState(index:Int): Int {
        return _ziker.arr[index].no_repeat
    }
    fun filterAzkar(name:String?){
        azkar.map { ziker: Ziker -> if (ziker.name == name) this._ziker = ziker }

    }
    fun isLastHadith(index:Int):Boolean{
        return _ziker.arr.size - 1 == index
    }
    fun isHadithFinish(index:Int):Boolean{
        return _ziker.arr[index].no_repeat == _ziker.arr[index].state

    }



    fun late_second() {

        _timerStateFlow.value = false

        viewModelScope.launch(Dispatchers.IO) {
            Thread.sleep(500)
            _timerStateFlow.value = true

        }

    }



}