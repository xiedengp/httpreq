package com.example.myapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapp.databinding.ActivitySateflowBinding
import com.example.myapp.viewmode.NumberViewMode

// stateFlow 的简单使用
// sharedFlow 一对多  这两个是热流
class StateFlowActivity : AppCompatActivity() {

    val binding: ActivitySateflowBinding by lazy {
        ActivitySateflowBinding.inflate(layoutInflater)
    }
    private val numberViewMode by viewModels<NumberViewMode>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            btnAdd.setOnClickListener {
                numberViewMode.add()
            }
            btnMinus.setOnClickListener {
                numberViewMode.minus()
            }
        }
        lifecycleScope.launchWhenStarted {
            numberViewMode.number.collect{
                binding.tvNumber.text = "$it"
            }
        }
    }


}