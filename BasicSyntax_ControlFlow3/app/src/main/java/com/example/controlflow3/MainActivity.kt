package com.example.controlflow3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 일반적인 when의 사용법
        var now = 9
        when (now) {
            8 -> {
                Log.d("when", "현재 시간은 8시 입니다")
            }
            9, 10 -> {
                Log.d("when", "현재 시간은 9시 또는 10시 입니다")
            }
            else -> { // 위의 모든 조건에 맞지 않으면 else 다음 코드가 실행된다.
                Log.d("when", "지금은 8시 ~ 10시가 아닙니다")
            }
        }
        // in 을 사용해서 범위를 비교할 수도 있다. 처리코드가 한줄이면 중괄호 생략가능
        var ageOfMichael = 19
        when (ageOfMichael) {
            in 10..19 -> Log.d("when", "마이클은 10대입니다")
            !in 10..19 -> Log.d("when", "마이클은 10대가 아닙니다")
            else -> Log.d("when", "마이클의 나이를 알 수 없습니다")
        }
        // 인자가 없이 if처럼 사용할 수도 있다.
        var currentTile = 6
        when  {
            currentTile == 5 -> {
                Log.d("when", "현재 시간은 5시 입니다")
            }
            currentTile > 5 -> {
                Log.d("when", "현재 시간은 5시가 넘었습니다")
            }
            else -> {
                Log.d("when", "현재 시간은 5시 이전입니다")
            }
        }
    }
}
