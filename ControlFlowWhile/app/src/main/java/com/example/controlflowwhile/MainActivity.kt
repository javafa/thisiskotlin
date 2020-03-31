package com.example.controlflowwhile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        // 1. 일반적인 while 사용하기
        var current = 1
        val until = 12
        while( current < until ) {
            Log.d("while", "현재값은 ${current} 입니다")
            // current를 1씩 증가시켜서 11번 반복한 후 while문을 빠져 나간다.
            current = current + 1
        }
        // 2. do~while 사용하기
        var game = 1
        val match = 6
        do {
            Log.d("while", "${game}게임 이겼습니다 우승까지 ${match-game}게임 남았습니다")
            game += 1
        } while( game < match )
        // 3. while vs do~while
        // while 테스트
        game = 6
        while( game < match ) {
            Log.d("while", "while 테스트 입니다")
            game += 1
        }
        // do ~ while 테스트
        game = 6
        do {
            Log.d("while", "do ~ while 테스트 입니다.")
            game += 1
        } while( game < match )
        // 4. break 반복문 탈출하기
        for(index in 1..10){
            Log.d("while", "break > 현재 index 는 $index 입니다")
            if(index > 5) { // index 가 5보다 크면 break 명령어로 현재 반복문을 벗어난다.
                break       // 따라서 Log 는 6까지만 출력된다.
            }
        }
        // 5. continue 다음 반복문으로
        for(except in 1..10){
            // except가 3보다 크고 8보다 작으면 continue 명령으로 로그를  찍지 않고 for문의 처음으로 jump 한다.
            if(except > 3 && except < 8){
                continue
            }
            // 따라서 4,5,6,7은 출력되지 않는다.
            Log.d("while", "continue > 현재 index 는 $except 입니다")
        }
        // 0. 무한루프 테스트
        // 이 아래의 주석을 해제하고 실행해 보세요
        // 무한루프에 빠지는 while 문 - 실행 후 멈추기 위해서는 우측 상단에 있는 빨간색 사각형 아이콘(Stop)을 클릭하면 된다.
//        var a = 1
//        while ( a == 1 ) {
//            Log.d("While", "조건을 만족하면 여기를 출력하세요!")
//        }
    }
}
