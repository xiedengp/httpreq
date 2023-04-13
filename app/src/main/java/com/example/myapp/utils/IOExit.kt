package com.example.myapp.utils

import java.io.InputStream
import java.io.OutputStream

inline fun InputStream.copyTo(
    out: OutputStream,
    buffSize: Int = DEFAULT_BUFFER_SIZE,
    progress: (Long) -> Unit
): Long {

    var bytesCopied: Long = 0
    val buff = ByteArray(buffSize)
    var bytes = read(buff)
    while (bytes > 0) {
        out.write(buff, 0, bytes)
        bytesCopied += bytes
        bytes = read(buff)
        progress(bytesCopied)
    }
    return bytesCopied
}