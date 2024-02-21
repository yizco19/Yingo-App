package com.zy.proyecto_final.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zy.proyecto_final.pojo.Time
import com.zy.proyecto_final.repository.TimeRepository
import kotlinx.coroutines.launch

class TimeViewModel: ViewModel() {
     var repository: TimeRepository
    private  var _time: Time

    init {
        this.repository = TimeRepository()
        this._time = Time()
    }
    // Use a coroutine scope to call the suspend function
    fun getTimeSync()  {
        viewModelScope.launch {
            _time = repository.getTime()
        }
    }
    fun getTime() : Time {
        getTimeSync()
        return _time
    }

}