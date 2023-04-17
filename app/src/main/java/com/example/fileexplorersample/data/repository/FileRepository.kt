package com.example.fileexplorersample.data.repository

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.example.fileexplorersample.util.WTF
import com.example.fileexplorersample.util.getExternalPath
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





    suspend fun getSizesByMimeType(context: Context) {
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
        var archivesSize = 0L
        var othersSize = 0L
        try {
            val cursor = context.contentResolver.query(uri, projection, null, null, null)
            WTF(cursor.toString())
            cursor?.let {
                cursor.apply {
                    val mimeType = getString(getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE) ?: 0)
                    WTF(mimeType)
                }
            }


        } catch (e: Exception) {
            e.stackTrace
        }


    }
}