package com.example.myapp.screen

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityListDataScreenBinding

class ListDataScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListDataScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDataScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listItem.setAdapter(ListDataScreenAdapter(this))

        binding.test.setOnClickListener {
            Toast.makeText(this,"test",Toast.LENGTH_SHORT).show()
        }
    }
}