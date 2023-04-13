package com.example.myapp.mode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.databinding.ActivityViewModeBinding

//viewMode liveData 简单监听使用
class ModeViewActivity : AppCompatActivity(){
    private lateinit var binding:ActivityViewModeBinding
    private lateinit var viewMode: MyViewMode
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewModeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewMode = ViewModelProvider(this).get(MyViewMode::class.java)
        binding.text.text = viewMode.num.toString()
        //监听数据变化
        viewMode.liveData.observe(this
        ) {
            println("liveData=$it")
        }
        binding.btn1.setOnClickListener {
            viewMode.num++
            binding.text.text = viewMode.num.toString()

            viewMode.addLiveNum(1)
        }

        binding.btn2.setOnClickListener {
            viewMode.num+=2
            binding.text.text = viewMode.num.toString()

            viewMode.addLiveNum(2)
        }
    }
}