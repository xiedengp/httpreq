package com.example.myapp.spanned

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R

/**
 * Created by Jorge Martín on 24/5/17.
 */
class MainTestActivity : AppCompatActivity() {

    val recyclerview: RecyclerView by lazy { findViewById(R.id.recycler_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_recyclerview)

        val spannedGridLayoutManager = SpannedGridLayoutManager(
            orientation = SpannedGridLayoutManager.Orientation.VERTICAL,
            spans = 4
        )
        spannedGridLayoutManager.itemOrderIsStable = true

        recyclerview.layoutManager = spannedGridLayoutManager

        recyclerview.addItemDecoration(
            SpaceItemDecorator(
                left = 10,
                top = 10,
                right = 10,
                bottom = 10
            )
        )

        val adapter = GridItemAdapter()

        if (savedInstanceState != null && savedInstanceState.containsKey("clicked")) {
            val clicked = savedInstanceState.getBooleanArray("clicked")!!
            adapter.clickedItems.clear()
            adapter.clickedItems.addAll(clicked.toList())
        }

        spannedGridLayoutManager.spanSizeLookup =
            SpannedGridLayoutManager.SpanSizeLookup { position ->
//            if (adapter.clickedItems[position]) {
//                SpanSize(4, 2)
//            } else {
//                SpanSize(1, 1)
//            }
                when (position) {
                    0 -> SpanSize(4, 3)
                    1 -> SpanSize(2, 1)
                    2 -> SpanSize(2, 2)
                    3 -> SpanSize(2, 1)
                    else -> {
                        SpanSize(4, 2)
                    }
                }
            }

        recyclerview.adapter = adapter
    } 

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBooleanArray(
            "clicked",
            (recyclerview.adapter as GridItemAdapter).clickedItems.toBooleanArray()
        )

    }

}