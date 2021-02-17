package kr.co.hanbit.miniquiz4_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.radioApple -> textView.text = "사과"
                R.id.radioBanana -> textView.text = "바나나"
                R.id.radioOrange -> textView.text = "오렌지"
            }
        }
    }
}
