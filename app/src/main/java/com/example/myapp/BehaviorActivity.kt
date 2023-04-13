package com.example.myapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.ActivityBehaviorBinding

class BehaviorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBehaviorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBehaviorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.myList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.myList.adapter = MyAdapter(this)

    }

    class MyAdapter(val context: Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val text: TextView = view.findViewById(R.id.text_num)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_number, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.text.text = position.toString()

        }

        override fun getItemCount(): Int = 50
    }
}