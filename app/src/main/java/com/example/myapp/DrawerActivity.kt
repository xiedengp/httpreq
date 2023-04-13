package com.example.myapp

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapp.databinding.ActivityDrawerBinding


class DrawerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrawerBinding
    private lateinit var  mToolbar: Toolbar
    private lateinit var mDrawerlayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        mToolbar = binding.idDrawerLayoutToolbar
        mDrawerlayout = binding.idDrawerLayout
        mDrawerlayout.close()
        mDrawerlayout.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
                println("onDrawerOpened")
            }

            override fun onDrawerClosed(drawerView: View) {
                println("onDrawerClosed")
            }

            override fun onDrawerStateChanged(newState: Int) {
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        getMenuInflater().inflate(R.menu.draw_layout_menu, menu)
        return true
    }
}