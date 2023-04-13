package com.example.myapp.vip

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityVipBinding

class VipEquityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVipBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vipEquity.setRow(5)

        binding.vipEquity.setAdapter(ViewEquityAdapter(this,Color.WHITE),0)
        binding.vipEquity.setAdapter(ViewEquityAdapter(this,Color.WHITE),1)
        binding.vipEquity.setAdapter(ViewEquityAdapter(this,Color.parseColor("#FBFCFF")),2)
        binding.vipEquity.setAdapter(ViewEquityAdapter(this,Color.parseColor("#F4F7FE")),3)
        binding.vipEquity.setAdapter(ViewEquityAdapter(this,Color.parseColor("#E9F0FD")),4)
        binding.vipEquity.setAdapter(ViewEquityAdapter(this,Color.parseColor("#FFF1E1")),5)
        binding.vipEquity.setAdapter(ViewEquityAdapter(this,Color.parseColor("#FFF1E1")),6)
        binding.vipEquity.setAdapter(ViewEquityAdapter(this,Color.parseColor("#FFF1E1")),7)
    }
}