package com.example.myapp.download

import java.io.File

/**
 * 下载的状态
 */
sealed class DownloadStatus {
    object None : DownloadStatus()

    data class Progress(val value: Int) : DownloadStatus()

    data class Error(val throws: Throwable) : DownloadStatus()

    data class Done(val file: File) : DownloadStatus()

}
