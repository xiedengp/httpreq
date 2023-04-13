package com.example.myapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import com.example.myapp.bean.GoodsBean
import com.example.myapp.database.WordDataBase
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.goodstyle.LoveStyleActivity
import com.example.myapp.loadingpoint.LoadingPointActivity
import com.example.myapp.mode.presenter.GoodPresenter
import com.example.myapp.mode.view.IGoodsView
import com.example.myapp.screen.ListDataScreenActivity
import com.example.myapp.spanned.MainTestActivity
import com.example.myapp.uiview.ColorTrackTextView
import com.example.myapp.uiview.MoveBesselView
import com.example.myapp.vip.VipEquityActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        lifecycle.addObserver(binding.chronometer)

        setShape()

        binding.leftToRight.setOnClickListener {
            setTrackTextCode(ColorTrackTextView.Direction.LEFT_TO_RIGHT)
        }
        binding.rightToLeft.setOnClickListener {
            setTrackTextCode(ColorTrackTextView.Direction.RIGHT_TO_LEFT)
        }

        binding.goActivity.setOnClickListener {
            startActivity(Intent(this, PwdActivity::class.java))
        }

        binding.goTag.setOnClickListener {
            startActivity(Intent(this, TagActivity::class.java))
        }
        binding.goDrag.setOnClickListener {
            startActivity(Intent(this, DragActivity::class.java))
        }

        binding.lock.setOnClickListener {
            startActivity(Intent(this, LockActivity::class.java))

        }
        binding.drawer.setOnClickListener {
            startActivity(Intent(this, DrawerActivity::class.java))

        }
        binding.behavior.setOnClickListener {
            startActivity(Intent(this, BehaviorActivity::class.java))
        }

        binding.listScreen.setOnClickListener {
            startActivity(Intent(this, ListDataScreenActivity::class.java))
        }
        binding.pointLoad.setOnClickListener {
            startActivity(Intent(this, LoadingPointActivity::class.java))
        }
        binding.bessel.setOnClickListener {
            startActivity(Intent(this, BesselActivity::class.java))
        }
        binding.listView.setOnClickListener {
            startActivity(Intent(this, VipEquityActivity::class.java))
        }
        binding.love.setOnClickListener {
            startActivity(Intent(this, LoveStyleActivity::class.java))
        }
        binding.land.setOnClickListener {
            startActivity(Intent(this,LandActivity::class.java))
        }
        binding.wordRoom.setOnClickListener {
            startActivity(Intent(this,DaoActivity::class.java))
        }
        binding.recyclerView.setOnClickListener {
            startActivity(Intent(this, MainTestActivity::class.java))
        }
        binding.flow.setOnClickListener {
//            val  intent =  Intent()
//            //抖音
//            intent.data = Uri.parse("snssdk1128://user/profile/106027410143")
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)

            startActivity(Intent(this, StateFlowActivity::class.java))

        }
        MoveBesselView.setMoveView(this, binding.bezier)
        ObjectAnimator.ofFloat(binding.bezier, "rotationY", 0f, 180f).apply {
            duration = 2000
            binding.bezier.cameraDistance = resources.displayMetrics.density * 2000
            interpolator = LinearInterpolator()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    binding.bezier.rotationY = 0f
                }
            })
            start()
        }

        setSeek()
        setStepView()
        setLetter()



    }

    private fun setLetter() {
        binding.letter.setCheckMoveKeyListener { s, isShow ->
            if (isShow) {
                binding.textLetter.visibility = View.VISIBLE
                binding.textLetter.text = s
            } else {
                binding.textLetter.postDelayed({
                    binding.textLetter.visibility = View.GONE
                }, 200)

            }
        }
    }

    private fun setSeek() {
        binding.progress.setMax(100)
        binding.progress.setCurrentValue(70)
    }

    private fun setStepView() {
        binding.step.setStepMax(4000)
        val objectAnimator = ObjectAnimator.ofFloat(0f, 3000f).apply {
            duration = 1000
        }
        objectAnimator.interpolator = DecelerateInterpolator()
        objectAnimator.addUpdateListener {
            val value = it.animatedValue.toString()
            binding.step.setStepCurrent(value.toFloat().toInt())
        }
        objectAnimator.start()
    }


    private fun setTrackTextCode(direction: ColorTrackTextView.Direction) {
        binding.trackText.setDirection(direction)
        ObjectAnimator.ofFloat(0f, 1f).apply {
            duration = 2000
            addUpdateListener {
                val value = it.animatedValue.toString()
                binding.trackText.setmSeek(value.toFloat())
            }
            start()
        }
    }

    var countDownTimer: CountDownTimer? = null
    private fun setShape() {
        if (null == countDownTimer)
            countDownTimer = object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.shape.changeShape()
                }

                override fun onFinish() {
                    setShape()
                }

            }
        countDownTimer?.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}