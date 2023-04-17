package com.example.fileexplorersample.common

object Constants {
    const val ROOT_TOTAL_SPACE = "PRIMARY_FOLDER_TOTAL"
    const val ROOT_FREE_SPACE = "PRIMARY_FOLDER_FREE"
}
object FileIdentify {
    val photoExtensions: Array<String> get() = arrayOf(".gif", ".jpg", ".png", ".jpeg", ".bmp", ".webp", ".heic", ".heif", ".apng", ".avif")
    val videoExtensions: Array<String> get() = arrayOf(".mp4", ".mkv", ".webm", ".avi", ".3gp", ".mov", ".m4v", ".3gpp")
    val audioExtensions: Array<String> get() = arrayOf(".mp3", ".wav", ".wma", ".ogg", ".m4a", ".opus", ".flac", ".aac")
}

object MimeTypeIdentify{
    const val IMAGES = "images"
    const val VIDEOS = "videos"
    const val AUDIO = "audio"
    const val DOCUMENTS = "documents"
    const val OTHERS = "others"
}