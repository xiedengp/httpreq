package com.example.myapp.bean;

import android.util.ArrayMap;

import androidx.lifecycle.LiveData;

/**
 * 文件传输进度更新通知工具
 */
public class HttpTransmissionProgressLive extends LiveData<HttpTransmissionProgressLive> {

    private static final ArrayMap<String, HttpTransmissionProgressLive> transmissionListeners = new ArrayMap<>(1);

    public static HttpTransmissionProgressLive getInstance(String url) {
        if (transmissionListeners.containsKey(url)) return transmissionListeners.get(url);
        synchronized (transmissionListeners) {
            HttpTransmissionProgressLive progressLive = new HttpTransmissionProgressLive();
            transmissionListeners.put(url, progressLive);
            return progressLive;
        }
    }

    public long currentLength;
    public long totalLength;
    public static boolean complete;
    public boolean isResponse;


    public void updateProgress(long currentLength, long totalLength, boolean isResponse) {
        this.currentLength = currentLength;
        this.totalLength = totalLength;
        this.isResponse = isResponse;
        postValue(this);
    }

    public void onComplete() {
        complete = true;
        postValue(this);
    }
}