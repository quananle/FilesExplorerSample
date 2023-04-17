package com.example.fileexplorersample.screen.home.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fileexplorersample.data.repository.FileRepository
import com.example.fileexplorersample.base.viewmodel.BaseViewModel
import com.example.fileexplorersample.common.FileIdentify
import com.example.fileexplorersample.util.WTF
import kotlinx.coroutines.launch
import java.io.File

class FolderViewModel(private val fileRepository: FileRepository) : BaseViewModel() {

    private var _files = MutableLiveData<List<File>>()
    val file: LiveData<List<File>> get() = _files

    private var _filteredFiles = MutableLiveData<List<File>>()
    val filteredFiles: LiveData<List<File>>
        get() = _filteredFiles

    fun setFilesFiltered(files: List<File>) {
        _filteredFiles.postValue(files)
    }

    fun onSearch(keyword: String){
        val fileClone = _files.value
        _filteredFiles.postValue(
            fileClone?.filter {
                it.name.contains(keyword)
            }
        )
    }


    fun getFiles(path: String = "") {
        viewModelScope.launch {
            _files.postValue(fileRepository.getFiles(path))
        }
    }

    fun sortFilesByName() {
        val fileClone = _files.value
        _filteredFiles.postValue(
            fileClone?.sorted()
        )
    }

    fun sortFilesByDate() {
        val fileClone = _files.value
        _filteredFiles.postValue(
            fileClone?.sortedByDescending { it.lastModified() }
        )
    }

    fun sortFilesBySize() {
        val fileClone = _files.value
        _filteredFiles.postValue(
            fileClone?.sortedByDescending { it.length()/1024 }
        )
    }

    fun filterFilesByType() {
        val fileClone = _files.value
        _filteredFiles.postValue(
            fileClone?.filter { file ->
                FileIdentify.photoExtensions.any{ suffix ->
                    file.absolutePath.endsWith(suffix)
                }
            }?.toList()
        )
    }

}