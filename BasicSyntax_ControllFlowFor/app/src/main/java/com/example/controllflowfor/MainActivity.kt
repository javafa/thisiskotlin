package com.example.controllflowfor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. 일반적인 반복문 사용으로 10번 반복하기
        for(index in 1..10){
            Log.d("For", "현재 숫자는 ${index}")
        }
        // 2. 마지막 숫자 제외하기
        var array = arrayOf("JAN","FEB","MAR","APR","MAY","JUN")
        for ( index in 0 until array.size ) {
            Log.d("For", "현재 월은 ${array.get(index)} 입니다")
        }
        // 3. 건너뛰기
        for(index in 0..10 step 3){
            Log.d("For", "건너뛰기 : ${index}")
        }
        // 4. 감소시키기
        for(index in 10 downTo 0) {
            Log.d("For", "감소시키기 : ${index}")
        }
        // 4.1 건너뛰면서 감소시키기
        for(index in 10 downTo 0 step 3) {
            Log.d("For", "건너뀌면서 감소시키기 : ${index}")
        }
        // 5.1 배열, 컬렉션 사용하기
        for(month in array) {
            Log.d("for", "현재 월은 ${month} 입니다")
        }
    }
}
