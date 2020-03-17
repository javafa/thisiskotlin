package com.example.controlflow1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var ball = 4;
        if (ball > 3) { 	// ball이 3보다 크면 if 다음에 있는 {블록}의 코드가 실행됩니다.
            Log.d("ControlFlow", "4볼로 출루합니다.")
        } else { 		// 그렇지 않으면 else 다음에 있는 {블록}의 코드가 실행됩니다.
            Log.d("ControlFlow", "타석에서 다음 타구를 기다립니다.")
        }

    }
}
