package com.example.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityTagBinding
import com.example.myapp.uiview.TagAdapter

class TagActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTagBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTagBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = mutableListOf("1111","22","3333","4444444","5555555555","66666")

        binding.tagView.setAdapter(object : TagAdapter(){
            override fun getItemCount(): Int  = list.size
            override fun childView(item: Int, group: ViewGroup?): View {
               val textView: TextView =  LayoutInflater.from(this@TagActivity).inflate(R.layout.item_tag,group,false) as TextView
                textView.text = list[item]
                return textView
            }

        })
    }
}