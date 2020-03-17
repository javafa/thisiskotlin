package com.example.immutablecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 생성
        val IMMUTABLE_LIST = listOf("JAN","FEB","MAR")
        // 사용
        Log.d("Collection","리스트의 2번째 값은 ${IMMUTABLE_LIST.get(1)} 입니다")

        val DAY_LIST = listOf("월","화","수","목","금","토","일")
    }
}
