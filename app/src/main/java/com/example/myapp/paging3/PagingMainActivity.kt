package com.example.myapp.paging3

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityPagingMainBinding

class PagingMainActivity : AppCompatActivity() {

    val bind : ActivityPagingMainBinding by lazy {
        ActivityPagingMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
    }
}