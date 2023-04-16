package com.example.fileexplorersample.screen.home.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fileexplorersample.data.repository.FileRepository
import com.example.fileexplorersample.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import java.io.File

class FolderViewModel(private val fileRepository: FileRepository) : BaseViewModel() {

    private var _files = MutableLiveData<List<File>>()
    val file: LiveData<List<File>> get() = _files

    fun getFiles(path: String = "") {
        viewModelScope.launch {
            _files.postValue(fileRepository.getFiles(path))
        }
    }

}