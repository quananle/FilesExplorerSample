package com.example.fileexplorersample.screen.home.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fileexplorersample.data.repository.FileRepository

class FolderViewModelFactory(private val fileRepository: FileRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FolderViewModel::class.java))
            return FolderViewModel(fileRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}