package kr.co.hanbit.miniquiz5_4_5

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
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

        // 빨간원 그리기
        val red = Paint()
        red.style = Paint.Style.FILL
        red.color = Color.RED
        canvas?.drawCircle(300f,300f, 200f, red)

        // 가운데 작은원 그리기
        val white = Paint()
        white.style = Paint.Style.FILL
        white.color = Color.WHITE
        canvas?.drawCircle(300f,300f, 150f, white)
    }
}