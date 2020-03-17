package com.example.widgetsratingbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1.0으로 레이팅바 초기화하기
        ratingBar.rating = 1.0F

        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            textView.text = "$rating"
        }
    }
}
