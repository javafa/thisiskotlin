package com.example.widgetsradio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.radioApple -> Log.d("RadioButton", "사과가 선택되었습니다.")
                R.id.radioBanana -> Log.d("RadioButton", "바나나가 선택되었습니다.")
                R.id.radioOrange -> Log.d("RadioButton", "오렌지가 선택되었습니다.")
            }
        }
    }
}
