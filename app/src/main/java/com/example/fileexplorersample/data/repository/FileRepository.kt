package com.example.fileexplorersample.data.repository

import android.os.Environment
import android.view.View
import com.example.fileexplorersample.Application
import com.example.fileexplorersample.util.WTF
import com.example.fileexplorersample.util.getExternalPath
import com.example.fileexplorersample.util.getFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class FileRepository {

    suspend fun getFiles(path: String = ""): ArrayList<File> {
        val files: ArrayList<File> = arrayListOf()
        val parent =
            if (path.isNotEmpty()) {
                File(path)
            }
            else {
                File(getExternalPath())
            }

        WTF(parent.absolutePath)

        if (parent.exists()) {
            val rootFiles = parent.listFiles()

            rootFiles?.let {
                if (it.isNotEmpty())
                    files.addAll(rootFiles)
            }
        }

       return files
    }
}