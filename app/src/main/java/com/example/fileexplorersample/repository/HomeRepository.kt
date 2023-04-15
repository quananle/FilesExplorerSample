package com.example.fileexplorersample.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class HomeRepository {

    suspend fun getFiles(path: String): List<File>? = withContext(Dispatchers.IO) {
        val dir = File(path)

        if (dir.isDirectory)
            dir.listFiles()?.toList()
        else
            null
    }
}