package com.example.fileexplorersample.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fileexplorersample.data.repository.FileRepository

class HomeViewModelFactory(private val fileRepository: FileRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(fileRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}