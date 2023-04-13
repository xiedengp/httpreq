package com.example.myapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityBesselBinding

class BesselActivity :AppCompatActivity() {
    private lateinit var binding: ActivityBesselBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBesselBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Toast.makeText(this,"",Toast.LENGTH_SHORT).show()
    }

}