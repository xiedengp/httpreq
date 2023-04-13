package com.example.myapp.uiview

import android.content.Context
import kotlin.jvm.JvmOverloads
import android.graphics.Bitmap
import android.content.res.TypedArray
import com.example.myapp.R
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.util.TypedValue
import android.view.View

class RatingBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val inStar: Bitmap
    private val outStar: Bitmap
    private var starNum = 5
    private var currentNum = 0
    private val paint: Paint
    private val starMind = 5//间隔大小

    init {
        val value = context.obtainStyledAttributes(attrs, R.styleable.RatingBar)
        val inRes = value.getResourceId(R.styleable.RatingBar_inStart, R.mipmap.star_no)
        val outRes = value.getResourceId(R.styleable.RatingBar_outStart, R.mipmap.star_yes)
        inStar = BitmapFactory.decodeResource(resources, inRes)
        outStar = BitmapFactory.decodeResource(resources, outRes)
        starNum = value.getInt(R.styleable.RatingBar_startNum, 5)
        paint = Paint()
        paint.isAntiAlias = true
        value.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = inStar.width * starNum + dip2px(starMind) * (starNum - 1)
        val height = inStar.height
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas);
        for (i in 0 until starNum) {
            val x = i * inStar.width + i * dip2px(starMind)
            if (currentNum > i) {
                canvas.drawBitmap(outStar, x.toFloat(), 0f, paint)
            } else {
                canvas.drawBitmap(inStar, x.toFloat(), 0f, paint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                //                break;
//            case MotionEvent.ACTION_UP: //抬起
//                break;
                val dx = event.x
                var mCurrentNum = (dx / (inStar.width + currentNum * starMind)).toInt() + 1
                if (mCurrentNum <= 0)
                    mCurrentNum = 1
                if (mCurrentNum > starNum)
                    mCurrentNum = starNum
                if (currentNum == mCurrentNum) return true
                currentNum = mCurrentNum
                invalidate()
            }
        }
        return true
    }

    private fun dip2px(dip: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
}