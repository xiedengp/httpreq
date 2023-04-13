package com.example.myapp.download

import com.example.myapp.utils.copyTo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException

object DownloadManage {
    fun download(url: String, file: File): Flow<DownloadStatus> {
        return flow {
            val request = Request.Builder().url(url).get().build()
            val response = OkHttpClient.Builder().build().newCall(request).execute()
            if (response.isSuccessful) {
                response.body()!!.let { body ->
                    val total = body.contentLength()
                    file.outputStream().use { output ->
                        val input = body.byteStream()
                        var emitProgress = 0L
                        input.copyTo(output) { bytesCopied ->
                            delay(50) //todo 下载速度太快了 搞一个延迟看看效果
                            val progress = bytesCopied * 100 / total
                            if (progress - emitProgress > 5) {
                                emit(DownloadStatus.Progress(progress.toInt())) //发送下载进度数据
                                emitProgress = progress
                            }
                        }
                    }
                }
                emit(DownloadStatus.Done(file)) //发送完成
            } else {
                throw  IOException(response.toString())
            }
        }.catch {
            file.delete()
            emit(DownloadStatus.Error(it)) //发送错误
        }
    }
}