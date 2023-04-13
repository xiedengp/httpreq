package com.example.myapp.spanned

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapp.R

/**
 * Created by Jorge Martín on 24/5/17.
 */
class GridItemAdapter : RecyclerView.Adapter<ViewHolder>() {
    companion object {
        const val TYPE_SMALL = 1
        const val TYPE_LARGE = 2
    }

    val clickedItems: MutableList<Boolean>

    init {
        clickedItems = MutableList(itemCount, { false })
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    val colors = arrayOf(
        Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.CYAN,
        Color.DKGRAY,
        Color.MAGENTA,
        Color.YELLOW
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        (holder.itemView as? GridItemView)?.setTitle("$position")

        holder.itemView.setBackgroundColor(
            colors[position % colors.size]
        )

//        holder.itemView.setOnClickListener {
//            clickedItems[position] = !clickedItems[position]
//            notifyItemChanged(position)
//        }
    }

    //设置返回哪一个样式
    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            return TYPE_SMALL
        } else {
            return TYPE_LARGE
        }
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val gridItemView = GridItemView(parent.context)

        val gridItemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_number_spannded, parent, false)
        val gridItemViewTwo = LayoutInflater.from(parent.context)
            .inflate(R.layout.item2_number, parent, false)
        if (viewType == TYPE_SMALL)
            return GridItemViewHolder(gridItemView)
        else
            return GridItemViewHolderTwo(gridItemViewTwo)
    }
}

class GridItemViewHolder(itemView: View) : ViewHolder(itemView)

class GridItemViewHolderTwo(itemView: View) : ViewHolder(itemView)