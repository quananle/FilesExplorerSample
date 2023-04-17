package com.example.fileexplorersample.base.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseViewModel: ViewModel() {
    var viewModelJob = Job()
    val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}