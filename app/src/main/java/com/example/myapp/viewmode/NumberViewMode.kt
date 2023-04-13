package com.example.myapp.viewmode

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class NumberViewMode : ViewModel() {
    //需要一个初始值
    var number = MutableStateFlow(0)

    fun add(){
        number.value++
    }

    fun minus(){
        number.value--
    }
}