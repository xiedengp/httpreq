package com.example.myapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.myapp.databinding.ActivityLandBinding
import com.lake.hbanner.*
import java.io.File


class LandActivity : AppCompatActivity() {
    private val TAG = "tag"
    private lateinit var binding: ActivityLandBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savedInstanceState?.run {
            binding.button2.text = getString("key")
        }
        binding.button2.setOnClickListener {
            binding.button2.text = "text"
        }

        binding.viewpage.post {
            val hBanner = HBanner.create(binding.viewpage as ViewPager?)
            val data: MutableList<SubView> = ArrayList()
            data.add(
                ImageSubView.Builder(baseContext)
                    .resId(R.mipmap.ic_launcher)
                    .duration(6000)
                    .build()
            )
            data.add(
                ImageSubView.Builder(baseContext)
                    .resId(R.mipmap.ic_launcher)
                    .duration(5000)
                    .build()
            )
            data.add(
                ImageSubView.Builder(baseContext)
                    .resId(R.mipmap.ic_launcher)
                    .duration(5000)
                    .build()
            )
            val appFileFolder: File = File(this.cacheDir, "update")
            if (!appFileFolder.exists() || !appFileFolder.isDirectory) {
                appFileFolder.mkdirs()
            }
            val appFile = File(appFileFolder, "update.mp4")
            if (appFile.exists()) {
                appFile.delete()
            }
            data.add(
                VideoSubView.Builder(baseContext)
//                    .file(appFile)
                    .url("https://highlight-video.cdn.bcebos.com//video//6s//bf859ff6-8949-11eb-b0f5-6c92bf5b3dce.mp4")
                    .gravity(VideoViewType.FULL)
                    .isSub(false)
                    .build()
            )

            hBanner.sources(data)
//设置viewpager切换方式
//设置viewpager切换方式

//开始显示或者自动播放

//开始显示或者自动播放
            hBanner.play(true)
        }



    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("key", binding.button2.text.toString())
        super.onSaveInstanceState(outState)
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}