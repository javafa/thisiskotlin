package com.example.customview

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val customView = CustomView("안녕 코틀린!",this)
        binding.frameLayout.addView(customView)
    }
}

class CustomView(text:String, context: Context) : View(context) {
    val text:String
    init {
        this.text = text
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 100f
        canvas?.drawText(text, 0f, 100f, paint)

        drawFigure(canvas)
    }

    fun drawFigure(canvas: Canvas?) {
        // 원 그리기
        val blue = Paint()
        blue.style = Paint.Style.FILL
        blue.color = Color.BLUE
        canvas?.drawCircle(150f,300f, 100f, blue)

        // 원 외곽선 그리기
        val red = Paint()
        red.style = Paint.Style.STROKE
        red.color = Color.RED
        canvas?.drawCircle(400f,300f, 100f, red)

        // 사각형 그리기
        val green = Paint()
        green.style = Paint.Style.STROKE
        green.strokeWidth = 20f
        green.color = Color.GREEN
        val rect = Rect(50, 450, 250, 650)
        canvas?.drawRect(rect, green)

        // 라운드 진 사각형 그리기
        val cyan = Paint()
        cyan.style = Paint.Style.FILL
        cyan.color = Color.CYAN
        val rectF = RectF(300f, 450f, 500f, 650f)
        canvas?.drawRoundRect(rectF, 50f, 50f, cyan)
    }
}