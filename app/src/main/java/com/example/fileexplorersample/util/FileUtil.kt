package com.example.fileexplorersample.util

import android.os.Environment
import android.provider.SyncStateContract.Constants
import com.example.fileexplorersample.common.FileIdentify
import java.io.File
import java.text.DecimalFormat
import java.util.*
import kotlin.math.log10
import kotlin.math.pow

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

fun File.isPhoto(): Boolean = FileIdentify.photoExtensions.any {
    this.absolutePath.trim().endsWith(it)
}

fun File.isMedia(): Boolean = isVideo() || isAudio()

fun File.isVideo(): Boolean =  FileIdentify.videoExtensions.any {
    this.absolutePath.trim().endsWith(it)
}

fun File.isAudio(): Boolean = FileIdentify.audioExtensions.any {
    this.absolutePath.trim().endsWith(it)
}

fun File.isAPK(): Boolean = this.absolutePath.trim().endsWith(".apk")

fun Long.formatFileSize(): String {
    if (this <= 0) {
        return "0 B"
    }

    val units = arrayOf("B", "kB", "MB", "GB", "TB")
    val digitGroups = (log10(toDouble()) / log10(1024.0)).toInt()
    return "${DecimalFormat("#,##0.#").format(this / 1024.0.pow(digitGroups.toDouble()))} ${units[digitGroups]}"
}


