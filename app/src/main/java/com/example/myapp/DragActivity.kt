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
import com.example.myapp.databinding.ActivityDragBinding

class DragActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDragBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDragBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val list = mutableListOf<String>()
        for (i in 0..99) {
            list.add(i.toString())
        }
        val adapter = DragAdapter(this, list)
        binding.recyclerView.adapter = adapter
    }

    class DragAdapter(
        val context: Context,
        val list: List<String>
    ) : RecyclerView.Adapter<DragAdapter.DragView>() {
        class DragView(view: View) : RecyclerView.ViewHolder(view) {

            val tetxVie: TextView = view.findViewById(R.id.text_num)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DragView {
            val view = LayoutInflater.from(context).inflate(R.layout.item_number, parent, false)
            return DragView(view)
        }

        override fun onBindViewHolder(holder: DragView, position: Int) {
            holder.tetxVie.text = list[position]
        }

        override fun getItemCount(): Int = list.size
    }
}