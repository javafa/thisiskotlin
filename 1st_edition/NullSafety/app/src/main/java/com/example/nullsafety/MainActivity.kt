package com.example.nullsafety

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        var one:One
//        if (1 > 2) {
//            one = One()
//        }
//        one.print()
        var nullable:String? = null
        var size = nullable?.length?:33
        Log.d("Nullable","문자열의 길이 = $size")

        nullParameter("새로운 문자열")

        val nValue = nullReturn()
        Log.d("Nullable", "${nValue}")

        Log.d("Null Safety", "length of null = ${testSafeCall(null)}")
        Log.d("Null Safety", "length of abc = ${testSafeCall("abc")}")

        Log.d("Null Safety", "length of null by elvis = ${testElvis("")}")
    }

    fun nullParameter(str:String?) {
        if (str != null) {
            var length2 = str.length
            Log.d("Null Safey","문자열 길이=$length2")
        }
        Log.d("Null Safey","문자열이 입력되지 않았습니다.")
    }

    fun nullReturn() : String? {
        return null
    }

    fun testSafeCall(str:String?) : Int? {
        // str이 null이면 length를 체크하지 않고 null을 반환합니다.
        var resultNull:Int? = str?.length
        return resultNull
    }

    fun testElvis(str:String?) : Int {
        // length 오른쪽에 ?: (콜론)을 사용하면 null일 경우 ?: 오른쪽의 값이 반환됩니다.
        var resultNonNull:Int = str?.length?:0
        return resultNonNull
    }
}

class One {
    fun print() {
        Log.d("null_safety","can you call me?")
    }
}
