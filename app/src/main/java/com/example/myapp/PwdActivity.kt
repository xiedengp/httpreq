package com.example.myapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.ActivityPwdBinding


class PwdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPwdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPwdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        val list: List<String> =
            mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "#", "0", "x")
        binding.recyclerView.adapter = MAdapter(this, list) {

            if (it.length >= 6) {
                Toast.makeText(this, "完成$it", Toast.LENGTH_SHORT).show()

            }
            binding.pwdView.setText(it)
        }


        binding.showNum.setOnClickListener {
            binding.loadView.visibility = GONE
            binding.pwdView.setIsShowPwd()
        }

        binding.mySeek.post {
            binding.mySeek.setSeekNum()
        }

    }

    class MAdapter(
        val context: Context,
        private val list: List<String>,
        private val str: (str: String) -> Unit
    ) : RecyclerView.Adapter<MAdapter.AdapterViewHolder>() {


        private val sb: StringBuffer = StringBuffer()

        class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.text_num)


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_number, parent, false)
            return AdapterViewHolder(view)
        }

        override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
            holder.textView.text = list[position]
            holder.textView.setOnClickListener {
                if (sb.length >= 6 && list[position] != "x") return@setOnClickListener
                if (list[position] == "x" && sb.isNotEmpty()) {

                    sb.deleteCharAt(sb.length - 1)
                    str.invoke(sb.toString())
                    return@setOnClickListener
                }
                if (list[position] == "x" && sb.isEmpty()) {
                    return@setOnClickListener
                }
                sb.append(list[position])
                str.invoke(sb.toString())
            }
        }

        override fun getItemCount(): Int = list.size
    }

}