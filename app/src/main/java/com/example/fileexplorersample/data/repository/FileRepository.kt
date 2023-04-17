package com.example.fileexplorersample.data.repository

import android.annotation.SuppressLint
import android.app.usage.StorageStatsManager
import android.content.Context
import android.os.storage.StorageManager
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.example.fileexplorersample.common.Constants
import com.example.fileexplorersample.common.MimeTypeIdentify
import com.example.fileexplorersample.util.WTF
import com.example.fileexplorersample.util.getExternalPath
import java.io.File
import java.util.*

class FileRepository {

    suspend fun getFiles(path: String = ""): ArrayList<File> {
        val files: ArrayList<File> = arrayListOf()
        val parent =
            if (path.isNotEmpty()) {
                File(path)
            } else {
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


    suspend fun getSizesByMimeType(context: Context) : Map<String, Long> {
        val uri = MediaStore.Files.getContentUri("external")
        val projection = arrayOf(
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.DATA
        )

        var imagesSize = 0L
        var videosSize = 0L
        var audioSize = 0L
        var documentsSize = 0L
        var othersSize = 0L

        try {
            val cursor = context.contentResolver.query(uri, projection, null, null, null)
            cursor?.let { values ->

                if (values.moveToFirst()) {
                    do {
                        val mimeType = values.getStringOrNull(values.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE))
                        val size = values.getLongOrNull(values.getColumnIndex(MediaStore.Files.FileColumns.SIZE)) ?: -1

                        mimeType?.let {

                            WTF(mimeType)//file has type

                            when (mimeType.substringBefore("/")) {
                                "image" -> imagesSize += size
                                "video" -> videosSize += size
                                "audio" -> audioSize += size
                                "text" -> documentsSize += size
                                else ->  othersSize += size
                            }

                        } ?: run { //file has not type
                            if (size > 0) { // but has size
                                val path = values.getStringOrNull(values.getColumnIndex(MediaStore.Files.FileColumns.DATA)) ?: ""
                                if (File(path).exists()) { // and exits
                                    othersSize += size
                                }
                            }
                        }
                    } while (values.moveToNext())
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        }

        val mimeTypeSizes = hashMapOf<String, Long>().apply {
            put(MimeTypeIdentify.IMAGES, imagesSize)
            put(MimeTypeIdentify.VIDEOS, videosSize)
            put(MimeTypeIdentify.AUDIO, audioSize)
            put(MimeTypeIdentify.DOCUMENTS, documentsSize)
            put(MimeTypeIdentify.OTHERS, othersSize)
        }

        return mimeTypeSizes
    }

    suspend fun getRootFolderInfo(context: Context) : Map<String, Long>? {
        val externalDirs = context.getExternalFilesDirs(null)
        val storageManager = context.getSystemService(AppCompatActivity.STORAGE_SERVICE) as StorageManager
         externalDirs.forEach { file ->
            val storageVolume = storageManager.getStorageVolume(file) ?: return@forEach
            if (storageVolume.isPrimary) {
                // internal storage
                val storageStatsManager = context.getSystemService(AppCompatActivity.STORAGE_STATS_SERVICE) as StorageStatsManager
                val uuid = StorageManager.UUID_DEFAULT
                val totalSpace = storageStatsManager.getTotalBytes(uuid)
                val freeSpace = storageStatsManager.getFreeBytes(uuid)
                return mapOf(
                    Constants.ROOT_TOTAL_SPACE to totalSpace,
                    Constants.ROOT_FREE_SPACE to  freeSpace
                )
            }
        }
        return null
    }
}