package com.example.fileexplorersample.util

import android.os.Environment
import java.io.File
import java.util.*

fun getFile(filePath: String) : File = File(
    Environment.getExternalStorageDirectory(),
    filePath
)

fun getExternalPath() : String{
    val externalPath: String = Environment.getExternalStorageDirectory().absolutePath
    val paths = externalPath
        .split("content://".toRegex())
        .dropLastWhile { it.isEmpty() }
        .toTypedArray()

    var parentPath = "/"
    for (s in paths) {
        if (s.trim { it <= ' ' }.isNotEmpty()) {
            parentPath += s
            break
        }
    }
    return parentPath
}

fun isImageFile(file: File): Boolean {
    val fileFilterImg = arrayOf(
        "jpg",
        "png",
        "gif",
        "jpeg"
    )
    for (filter in fileFilterImg) {
        if (file.absolutePath.trim().endsWith(file.name))
            return true
        }
    return false
}

