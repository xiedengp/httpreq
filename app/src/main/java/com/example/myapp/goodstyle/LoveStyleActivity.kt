package com.example.myapp.goodstyle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityStyleLoveBinding

class LoveStyleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStyleLoveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStyleLoveBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.love.setOnClickListener {
            binding.loveGood.initLayout()
        }
    }
}