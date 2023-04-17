package com.example.fileexplorersample.screen.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fileexplorersample.base.viewmodel.BaseViewModel
import com.example.fileexplorersample.common.MimeTypeIdentify
import com.example.fileexplorersample.data.repository.FileRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val fileRepository: FileRepository) : BaseViewModel() {

    private var _mimeTypeSize = MutableLiveData<Map<String, Long>>()
    val mimeTypeSize : LiveData<Map<String, Long>> get() = _mimeTypeSize

    private var _rootInfo = MutableLiveData<Map<String, Long>>()
    val rootInfo : LiveData<Map<String, Long>> get() = _rootInfo

    fun getMimeTypeSize(context: Context) {
       viewModelScope.launch {
           _mimeTypeSize.postValue(
               fileRepository.getSizesByMimeType(context)
           )
       }
    }

    fun getRootFolderInfo(context: Context) {
        viewModelScope.launch {
            _rootInfo.postValue(
                fileRepository.getRootFolderInfo(context)
            )
        }
    }


}