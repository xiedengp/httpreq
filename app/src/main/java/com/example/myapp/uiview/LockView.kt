package com.example.myapp.uiview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin

class LockView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    var onLockListener: ((select: ArrayList<Point>) -> Unit)? = null

    private var mPoints = Array(3) { Array<Point?>(3) { null } }
    private var mIsInt = false //是否初始化

    private var mIsTouchPoint = false //是否开始touch
    private var mSelectPoint = ArrayList<Point>()

    private var mDotRadius = 0 //圆心点大小

    //画笔
    private lateinit var mLinePaint: Paint
    private lateinit var mPressedPaint: Paint
    private lateinit var mErrorPaint: Paint
    private lateinit var mNormalPaint: Paint
    private lateinit var mArrowPaint: Paint

    //颜色
    private val mOutPressedColor = 0xff8cbad8.toInt()
    private val mInPressedColor = 0xff0596f6.toInt()
    private val mOutNormalColor = 0xffd9d9d9.toInt()
    private val mInNormalColor = 0xff929292.toInt()
    private val mOutErrorColor = 0xff901032.toInt()
    private val mInErrorColor = 0xffea0945.toInt()

    override fun onDraw(canvas: Canvas?) {
        if (!mIsInt) {
            initDot()
            initPaint()
            mIsInt = true
        }
        drawShowView(canvas)
    }

    //9宫格显示
    private fun drawShowView(canvas: Canvas?) {
        for (i in 0..2) {
            for (point in mPoints[i]) {
                when (point!!.status) {

                    Point.STATUS_ERROR -> {
                        //绘制外圆
                        mErrorPaint.color = mOutErrorColor
                        canvas?.drawCircle(
                            point.centerX.toFloat(),
                            point.centerY.toFloat(),
                            mDotRadius.toFloat(),
                            mErrorPaint
                        )

                        //绘制内圆
                        mErrorPaint.color = mInErrorColor
                        canvas?.drawCircle(
                            point.centerX.toFloat(),
                            point.centerY.toFloat(),
                            mDotRadius / 6.toFloat(),
                            mErrorPaint
                        )
                    }
                    Point.STATUS_PRESSED -> {
                        //绘制外圆
                        mPressedPaint.color = mOutPressedColor
                        canvas?.drawCircle(
                            point.centerX.toFloat(),
                            point.centerY.toFloat(),
                            mDotRadius.toFloat(),
                            mPressedPaint
                        )

                        //绘制内圆
                        mPressedPaint.color = mInPressedColor
                        canvas?.drawCircle(
                            point.centerX.toFloat(),
                            point.centerY.toFloat(),
                            mDotRadius / 6.toFloat(),
                            mPressedPaint
                        )
                    }
                    Point.STATUS_NORMAL -> {
                        //绘制外圆
                        mNormalPaint.color = mOutNormalColor
                        canvas?.drawCircle(
                            point.centerX.toFloat(),
                            point.centerY.toFloat(),
                            mDotRadius.toFloat(),
                            mNormalPaint
                        )

                        //绘制内圆
                        mNormalPaint.color = mInNormalColor
                        canvas?.drawCircle(
                            point.centerX.toFloat(),
                            point.centerY.toFloat(),
                            mDotRadius / 6.toFloat(),
                            mNormalPaint
                        )
                    }
                }


            }
        }

        //绘制连线和箭
        if (mIsInt)
            drawLine(canvas)
    }

    private fun drawLine(canvas: Canvas?) {
        canvas?.apply {

            if (mSelectPoint.size >= 1) {

                //检测点的线路
                for (index in 0 until mSelectPoint.size) {
                    if (index + 1 < mSelectPoint.size) {
                        if (mSelectPoint[index].index == 0) {
                            when (mSelectPoint[index + 1].index) {
                                2 -> {
                                    if (!mSelectPoint.contains(mPoints[0][1]!!)) {
                                        mPoints[0][1]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[0][1]!!)
                                    }

                                }
                                6 -> {
                                    if (!mSelectPoint.contains(mPoints[1][0]!!)) {
                                        mPoints[1][0]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[1][0]!!)
                                    }

                                }
                                8 -> {
                                    if (!mSelectPoint.contains(mPoints[1][1]!!)) {
                                        mPoints[1][1]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[1][1]!!)
                                    }

                                }
                            }
                        } else if (mSelectPoint[index].index == 1) {
                            when (mSelectPoint[index + 1].index) {
                                7 -> {
                                    if (!mSelectPoint.contains(mPoints[1][1]!!)) {
                                        mPoints[1][1]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[1][1]!!)
                                    }

                                }
                            }
                        } else if (mSelectPoint[index].index == 2) {
                            when (mSelectPoint[index + 1].index) {
                                0 -> {
                                    if (!mSelectPoint.contains(mPoints[0][1]!!)) {
                                        mPoints[0][1]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[0][1]!!)
                                    }

                                }
                                6 -> {
                                    if (!mSelectPoint.contains(mPoints[1][1]!!)) {
                                        mPoints[1][1]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[1][1]!!)
                                    }

                                }
                                8 -> {
                                    if (!mSelectPoint.contains(mPoints[1][2]!!)) {
                                        mPoints[1][2]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[1][2]!!)
                                    }

                                }
                            }
                        } else if (mSelectPoint[index].index == 3) {
                            when (mSelectPoint[index + 1].index) {
                                5 -> {
                                    if (!mSelectPoint.contains(mPoints[1][1]!!)) {
                                        mPoints[1][1]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[1][1]!!)
                                    }

                                }
                            }
                        } else if (mSelectPoint[index].index == 5) {
                            when (mSelectPoint[index + 1].index) {
                                3 -> {
                                    if (!mSelectPoint.contains(mPoints[1][1]!!)) {
                                        mPoints[1][1]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[1][1]!!)
                                    }

                                }
                            }
                        } else if (mSelectPoint[index].index == 6) {
                            when (mSelectPoint[index + 1].index) {
                                0 -> {
                                    if (!mSelectPoint.contains(mPoints[1][0]!!)) {
                                        mPoints[1][0]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[1][0]!!)
                                    }
                                }
                                2 -> {
                                    if (!mSelectPoint.contains(mPoints[1][1]!!)) {
                                        mPoints[1][1]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[1][1]!!)
                                    }
                                }
                                8 -> {
                                    if (!mSelectPoint.contains(mPoints[2][1]!!)) {
                                        mPoints[2][1]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[2][1]!!)
                                    }
                                }
                            }
                        } else if (mSelectPoint[index].index == 7) {
                            when (mSelectPoint[index + 1].index) {
                                1 -> {
                                    if (!mSelectPoint.contains(mPoints[1][1]!!)) {
                                        mPoints[1][1]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[1][1]!!)
                                    }
                                }
                            }
                        } else if (mSelectPoint[index].index == 8) {
                            when (mSelectPoint[index + 1].index) {
                                0 -> {
                                    if (!mSelectPoint.contains(mPoints[1][1]!!)) {
                                        mPoints[1][1]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[1][1]!!)
                                    }
                                }
                                2 -> {
                                    if (!mSelectPoint.contains(mPoints[1][2]!!)) {
                                        mPoints[1][2]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[1][2]!!)
                                    }
                                }
                                6 -> {
                                    if (!mSelectPoint.contains(mPoints[2][1]!!)) {
                                        mPoints[2][1]!!.setStatusPressed()
                                        mSelectPoint.add(index + 1, mPoints[2][1]!!)
                                    }
                                }
                            }
                        }

                    }

                }


                //亮点绘制连线
                var lastPoint = mSelectPoint[0]
                if (lastPoint.status == Point.STATUS_ERROR) {
                    mLinePaint.color = mInErrorColor
                }
                for (index in 1 until mSelectPoint.size) {

                    //绘制线
                    drawLine(lastPoint, mSelectPoint[index], canvas, mLinePaint)
                    //绘制箭头

                    lastPoint = mSelectPoint[index]
                }
                if (lastPoint.status != Point.STATUS_ERROR)
                    drawLine(lastPoint, Point(downX.toInt(), downY.toInt(), -1), canvas, mLinePaint)
            }


        }

    }

    private fun drawLine(start: Point, end: Point, canvas: Canvas, paint: Paint) {

        val n = atan(abs(start.centerY - end.centerY) / abs(start.centerX - end.centerX).toDouble())
        var X1 = 0.0
        var X2 = 0.0
        var Y1 = 0.0
        var Y2 = 0.0
        val centerX1 = start.centerX
        val centerX2 = end.centerX
        val centerY1 = start.centerY
        val centerY2 = end.centerY
        val radius = mDotRadius / 6f;
        if (start.centerX >= end.centerX) {
            X1 = centerX1 - radius * cos(n)
            X2 = centerX2 + radius * cos(n)
        } else {
            X1 = centerX1 + radius * cos(n)
            X2 = centerX2 - radius * cos(n)
        }
        if (centerY1 >= centerY2) {
            Y1 = centerY1 - radius * sin(n)
            Y2 = centerY2 + radius * sin(n)
        } else {
            Y1 = centerY1 + radius * sin(n)
            Y2 = centerY2 - radius * sin(n)
        }


        canvas.drawLine(
            X1.toFloat(), Y1.toFloat(),
            X2.toFloat(), Y2.toFloat(), paint
        )

    }

    /**
     * 初始化画笔
     * 3个点状态画笔  线点画笔 三角形点箭头
     */
    private fun initPaint() {
        //线点画笔
        mLinePaint = Paint().apply {
            color = mInPressedColor
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeWidth = (mDotRadius / 9).toFloat()
        }

        //按下
        mPressedPaint = Paint().apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeWidth = (mDotRadius / 6).toFloat()
        }

        //错误
        mErrorPaint = Paint().apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeWidth = (mDotRadius / 6).toFloat()
        }

        //默认
        mNormalPaint = Paint().apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeWidth = (mDotRadius / 9).toFloat()
        }

        //箭头
        mArrowPaint = Paint().apply {
            color = mInPressedColor
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeWidth = (mDotRadius / 9).toFloat()
        }
    }

    //初始化点
    private fun initDot() {
        mDotRadius = width / 12
        //todo 可以判断一下横竖屏
        val squareWidth = width / 3
        val offsetY = (height - width) / 2
        mPoints[0][0] = Point(squareWidth / 2, offsetY + squareWidth / 2, 0)
        mPoints[0][1] = Point(squareWidth * 3 / 2, offsetY + squareWidth / 2, 1)
        mPoints[0][2] = Point(squareWidth * 5 / 2, offsetY + squareWidth / 2, 2)
        mPoints[1][0] = Point(squareWidth / 2, offsetY + squareWidth * 3 / 2, 3)
        mPoints[1][1] = Point(squareWidth * 3 / 2, offsetY + squareWidth * 3 / 2, 4)
        mPoints[1][2] = Point(squareWidth * 5 / 2, offsetY + squareWidth * 3 / 2, 5)
        mPoints[2][0] = Point(squareWidth / 2, offsetY + squareWidth * 5 / 2, 6)
        mPoints[2][1] = Point(squareWidth * 3 / 2, offsetY + squareWidth * 5 / 2, 7)
        mPoints[2][2] = Point(squareWidth * 5 / 2, offsetY + squareWidth * 5 / 2, 8)
    }

    private var downX: Float = 0f
    private var downY: Float = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        downY = event.y
        downX = event.x
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //判断点是否在圆里面 点到圆心点距离 小于 半径
                val point: Point? = checkPoint()
                if (null != point) {
                    mIsTouchPoint = true
                    mSelectPoint.add(point)
                }


            }
            MotionEvent.ACTION_MOVE -> {
                val point: Point? = checkPoint()
                if (null != point && !mSelectPoint.contains(point)) {
                    mIsTouchPoint = true
                    mSelectPoint.add(point)

                }
            }
            MotionEvent.ACTION_UP -> {
                //回调密码监听  先回调 在清空
                onLockListener?.invoke(mSelectPoint)
//                mSelectPoint.clear()
//                mIsTouchPoint = false
//                mIsInt = false
                setErrorStatus()
            }
        }
        invalidate()
        return true
    }

    private fun setErrorStatus() {
        mSelectPoint.forEach {
            it.status = Point.STATUS_ERROR
        }
        invalidate()
        this.postDelayed({
            mSelectPoint.clear()
            mIsTouchPoint = false
            mIsInt = false
            invalidate()
        }, 500)
    }

    private fun checkPoint(): Point? {
        for (i in mPoints.indices) {
            for (j in 0 until mPoints[i].size) {

                val point: Point = mPoints[i][j]!!
                val isCircle = MathUtil.checkInRound(
                    downX,
                    downY,
                    mDotRadius.toFloat(),
                    point.centerX.toFloat(),
                    point.centerY.toFloat(),
                )
                if (isCircle) {
                    point.setStatusPressed()
                    return point
                }


            }
        }
        return null
    }


    class Point(var centerX: Int, var centerY: Int, var index: Int) {
        //点点状态
        companion object {
            const val STATUS_NORMAL = 0 //默认
            const val STATUS_PRESSED = 1 //选中
            const val STATUS_ERROR = 2 //错误
        }

        var status = STATUS_NORMAL

        fun setStatusPressed() {
            status = STATUS_PRESSED
        }


    }
}