package com.example.myapp

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapp.databinding.ActivityLockBinding
import com.example.myapp.download.DownloadManage
import com.example.myapp.download.DownloadStatus
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOn
import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class LockActivity : AppCompatActivity() {
    private lateinit var bind: ActivityLockBinding
    private val mainScope = MainScope()
    private val url =
        "https://dmcdn.qiguangtech.cn/video/20230327/7395a723f74c69d87a8f5406a608c38e.mp4"

    companion object {
        const val TAG = "LockActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        StatusBarUtil.steTranslucent(this)
        bind = ActivityLockBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.lock.onLockListener = {
            it.forEach {
                println("itPoint=${it.index}")
            }
        }


//        download(this)
//        test()
        notNullTest()
    }

    /**
     * flow 实现下载传递进度
     */
    private fun download(context: Context) {
        val file = File(context.cacheDir.path, "fileName")
        lifecycleScope.launch {

            DownloadManage.download(url, file).flowOn(Dispatchers.IO).collect { status ->
                Log.e("TAG", "download: $status")
                when (status) {
                    is DownloadStatus.Progress -> {
                        withContext(Dispatchers.Main) {
                            bind.seekBar.progress = status.value
                            bind.seekNum.text = "${status.value}%"
                        }
                    }
                    is DownloadStatus.Error -> Toast.makeText(
                        context,
                        "下载错误",
                        Toast.LENGTH_SHORT
                    ).show()
                    is DownloadStatus.Done -> {
                        bind.seekBar.progress = 100
                        bind.seekNum.text = "100%"
                        Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show()

                    }
                    else -> {
                        Log.e("TAG", "download: 下载失败")
                    }
                }
            }
        }

    }


    private fun test() {
        List(5) {
            Log.e("TAG", "test: $it")
        }



        repeat(4) {
            Log.e("repeat", "test: $it")
        }


        val str = "qewrssssfggghh"
        println("total=" + str.count())
        println("total=" + str.count {
            it == 's'
        })

        //匿名函数 不需要return 极少需要  最后一句话作为返回值
        val blessingFunction: () -> String = {
            "happy new year"
        }

        val hour = (1..24).shuffled().last()
        //自动推导内型
        val blessingFunctionP = { name: String, age: Int ->
            " $name happy new year $age"
        }

        println(blessingFunctionP("jack", 2027))

    }


    private fun notNullTest() {
        var str: String? = "test"
        //let 函数 返回最后一行值 作为返回值
        str?.let {
            if (it.isNotBlank()) {
                it.capitalize()
            } else {
                "butterfly"
            }
        }
        val s = str ?: "butterfly" // ?: 空合并操作符

        //截取字符串
        val name = "jimmy's friend"
        val indexOf = name.indexOf("\'")
        val sName = name.subSequence(0 until indexOf)
        Log.e(TAG, "notNullTest: $sName")


        val names = "jack,jacky,json"
        val split = names.split(",")
        val (o, _, p) = split // todo 解构语法 给三个变量赋值  _ 下划线跳过赋值
        split.forEach {
            Log.e(TAG, "notNullTest: $it")
        }


        val str1 = "The people's Republic  of china"
        //todo Regex 正则表达式
        val str2 = str1.replace(Regex("[aeiou]")) {
            when (it.value) {
                "a" -> "9"
                "e" -> "7"
                "i" -> "4"
                "o" -> "2"
                "u" -> "5"
                else -> it.value
            }


        }
        Log.e(TAG, "notNullTest: $str1")
        Log.e(TAG, "notNullTest: $str2")


        //字符串比较 kotlin == 比较值  === 比较对象 是否指向同一个引用  todo 两个都是true  同一个常量池里 指向的同一个对象
        val s1 = "Json"
        val s2 = "Json"
        Log.e(TAG, "notNullTest: ${s1 == s2}")
        Log.e(TAG, "notNullTest: ${s1 === s2}")


        //安全转换 失败就是 null
        val number: Int? = "7.87".toIntOrNull()

        8.9876559.roundToInt() //四舍五入
        8.9876559.toInt() //不处理
        // ---->
        val s4 = "%.2f".format(8.98768765)
        Log.e(TAG, "notNullTest: $s4")


        //标准函数理解 apply  可以看作配置函数 返回本身
        val paint = Paint()
        paint.apply {
            isAntiAlias = true
            alpha = 40
            color = Color.BLACK
        }

        //let 函数 let 返回最后一句 作为返回值
        val result = listOf(3, 2, 1).first().let {
            it * it
        }
        Log.e(TAG, "let notNullTest: $result")
        Log.e(TAG, "let notNullTest: ${format("jack")}")

        //run  this 接收值  返回最后一行语句执行的结果  没返回值 就没有返回值
        paint.run {
            isAntiAlias = true
            isDither = true
            color = Color.BLUE
        }
        str1.run {
            this.length>10
        }

        //with 传参数不同 this 这个使用较少
        with(str1){
            length>10
        }

        //also函数 返回自身 不会更改
        val list  = "list1"
        list.also {
            println(TAG + it)
            it+"hhhhh"
        }
        Log.e(TAG, "notNullTest: also -- >$list" )

        //takeIf 先判断 里面是true todo 返回原始对象 会执行后面 false 返回null 后面就不会执行
        str1.takeIf {
            it.contains("ch")
        }?.apply {
            Log.e(TAG, "notNullTest: takeIf-> $this" )
        }

        //takeUnless 跟takeId 相反
        str1.takeUnless {
            it.contains("ch")
        }?.apply {
            Log.e(TAG, "notNullTest: takeIf-> $this" )
        }
    }

    private fun format(guestName: String?): String {
        return guestName?.let {
            "welcome $it"
        } ?: "what's your name"

    }


    private fun listTest(){
        //不可变
        val list = listOf("json", "jack", "jacky")
        //没有就去默认值
        list.getOrElse(3){
            "unknown"
        }
        //没有就返回null
        list.getOrNull(3)

        list.toMutableList().toList() // 相互转换

        //可变的
        val mutableListOf = mutableListOf("11", "22", "33")
        mutableListOf.add("yy")
        mutableListOf.remove("11")
        //运算符重载
        mutableListOf += "55"
        mutableListOf -= "33"
        Log.e(TAG, "listTest: $mutableListOf" )
        mutableListOf.forEachIndexed { index, s ->
            Log.e(TAG, "listTest: $index --> $s" )
        }



    }
    class Test(name: String,age :Int){
        //次构造函数
        constructor(name:String) :this(name,10)
        
        var name = "jack"
        get() = field.capitalize ()
        set(value)  {
            field = value.trim()
        }

        var age = 20
        get() = field.absoluteValue
        set(value) {
            field = value.absoluteValue
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }


}