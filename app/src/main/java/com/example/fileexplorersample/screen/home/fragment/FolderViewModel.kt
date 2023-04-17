package com.example.fileexplorersample.screen.home.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fileexplorersample.data.repository.FileRepository
import com.example.fileexplorersample.base.viewmodel.BaseViewModel
import com.example.fileexplorersample.common.FileIdentify
import com.example.fileexplorersample.util.WTF
import com.example.fileexplorersample.util.isPhoto
import kotlinx.coroutines.launch
import java.io.File

class FolderViewModel(private val fileRepository: FileRepository) : BaseViewModel() {

    private var _files = MutableLiveData<List<File>>()
    val file: LiveData<List<File>> get() = _files

    fun getFiles(path: String = "") {
        WTF(path)
        viewModelScope.launch {
            _files.postValue(fileRepository.getFiles(path))
        }
    }

    fun sortFilesByDate() {
        _files.postValue(
            _files.value?.sortedByDescending { it.lastModified() }
        )
    }

    fun sortFilesBySize() {
        _files.postValue(
            _files.value?.sortedByDescending { it.length()/1024 }
        )
    }

    fun filterFilesByType() {
        _files.postValue(
            _files.value?.filter { file ->
                FileIdentify.photoExtensions.any{ suffix ->
                    file.absolutePath.endsWith(suffix)
                }
            }?.toList()
        )
    }

}