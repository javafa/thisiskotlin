package com.example.collectionset

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 1. 셋 생성하고 값 추가하기
        var set = mutableSetOf<String>()
        set.add("JAN")
        set.add("FEB")
        set.add("MAR")
        set.add("JAN") // 동일한 값은 입력되지 않는다.
        // 2. 전체 데이터 출력해보기
        Log.d("Collection", "Set 전체출력 = ${set}")
        // 3. 특정 값 삭제하기
        set.remove("FEB")
        Log.d("Collection", "Set 전체출력 = ${set}")
    }
}
