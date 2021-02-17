package com.example.widgetscheckbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        if(isChecked) {
            when (buttonView.id) {
                R.id.checkApple -> Log.d("CheckBox", "사과가 선택되었습니다")
                R.id.checkBanana -> Log.d("CheckBox", "바나나가 선택되었습니다")
                R.id.checkOrange -> Log.d("CheckBox", "오렌지가 선택되었습니다")
            }
        } else {
            when (buttonView.id) {
                R.id.checkApple -> Log.d("CheckBox", "사과가 선택 해제되었습니다")
                R.id.checkBanana -> Log.d("CheckBox", "바나나가 선택 해제되었습니다")
                R.id.checkOrange -> Log.d("CheckBox", "오렌지가 선택 해제되었습니다")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkApple.setOnCheckedChangeListener(listener)
        checkBanana.setOnCheckedChangeListener(listener)
        checkOrange.setOnCheckedChangeListener(listener)
    }
}
