package com.example.function

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 4. 반환값이 있는 함수 square 사용하기
        var squareResult = square(30)
        Log.d("fun", "30의 제곱은은 ${squareResult}입니다")

        // 5. 반환값이 없는 함수는 그냥 실행한다
        printSum(3,5)

        // 6. 입력값이 없는 함수 사용
        val PI = getPi()
        Log.d("fun", "지름이 10인 원의 둘레는 ${10 * PI}입니다")

        // 7. 기본값이 있는 함수 사용
        newFunction("Hello")

        // 8. 파라미터 이름을 직접 지정하기
        newFunction("Michael", weight=67.5)

        // 문제풀이 호출
        Log.d("fun", "5와7을 더한 결과값은 ${plus(5, 7)}입니다")
        Log.d("fun", "0부터 100을 모두 더한 결과값은 ${sum(100)}입니다")
        printString("문자열")
    }
    // 1. 반환값이 있는 함수
    fun square(x: Int): Int {
        return x * x  // <- square 함수는 입력받은 값에 2를 곱해서 반환한다.
    }
    // 2. 반환값이 없는 함수
    fun printSum(x: Int, y:Int) {
        Log.d("fun", "x + y = ${x+y}")
    }
    // 3. 입력값 없이 반환값만 있는 함수
    fun getPi(): Double {
        return 3.14
    }
    // 7. 기본값을 갖는 함수
    fun newFunction(name:String, age:Int = 29, weight:Double = 65.5) {
        Log.d("fun","name의 값은 ${name}입니다")
        Log.d("fun","age의 값은 ${age}입니다")
        Log.d("fun","weight의 값은 ${weight}입니다")
    }
    // 문제 1
    fun plus(x: Int, y:Int) : Int {
        return x + y
    }
    // 문제 2
    fun sum(x: Int) : Int {
        var result = 0
        for(num in 0..x) {
            result += num
        }
        return result
    }
    // 문제 3
    fun printString(word: String) {
        System.out.println(word)
    }
}
