package kr.co.hanbit.miniquiz5_4_4

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val customView = CustomView(this)
        frameLayout.addView(customView)
    }
}


class CustomView(context: Context) : View(context) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawFigure(canvas)
    }

    fun drawFigure(canvas: Canvas?) {

        // 라운드 진 사각형 그리기
        val rectF = RectF(200f, 450f, 600f, 650f)
        val rectPaint = Paint()
        rectPaint.color = Color.BLUE
        rectPaint.style = Paint.Style.FILL
        canvas?.drawRoundRect(rectF, 50f, 50f, rectPaint)

        rectPaint.color = Color.RED
        rectPaint.style = Paint.Style.STROKE
        rectPaint.strokeWidth = 10f
        canvas?.drawRoundRect(rectF, 50f, 50f, rectPaint)
    }
}