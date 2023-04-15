package com.example.fileexplorersample.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fileexplorersample.repository.HomeRepository
import com.example.fileexplorersample.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import java.io.File

class HomeViewModel(private val homeRepository: HomeRepository) : BaseViewModel() {

    private var _files = MutableLiveData<List<File>>()
    val file: LiveData<List<File>> get() = file

    fun getFiles(path: String) {
        viewModelScope.launch {
            _files.postValue(homeRepository.getFiles(path))
        }
    }

}