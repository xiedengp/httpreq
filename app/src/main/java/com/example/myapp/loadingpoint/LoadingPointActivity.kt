package com.example.myapp.loadingpoint

import android.os.Bundle
import android.os.Handler
import android.view.View.GONE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityLoadingPointBinding

class LoadingPointActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoadingPointBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingPointBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.text.setOnClickListener {
            Toast.makeText(this, binding.text.text, Toast.LENGTH_SHORT).show()
        }
        binding.load.setOnClickListener {
            binding.load.visibility = GONE
        }

        Handler().postDelayed({
            binding.loadView.loadEndAnimator()
        }, (2000).toLong()) //延迟10秒执行


    }
}