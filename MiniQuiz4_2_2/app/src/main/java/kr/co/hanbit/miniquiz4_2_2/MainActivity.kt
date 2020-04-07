package kr.co.hanbit.miniquiz4_2_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var apple = false
    var banana = false
    var orange = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkApple.setOnCheckedChangeListener { buttonView, isChecked ->
            apple = isChecked
            printCheckedItems()
        }
        checkBanana.setOnCheckedChangeListener { buttonView, isChecked ->
            banana = isChecked
            printCheckedItems()
        }
        checkOrange.setOnCheckedChangeListener { buttonView, isChecked ->
            orange = isChecked
            printCheckedItems()
        }
    }

    fun printCheckedItems(){
        var result = ""
        if(apple) result = " 사과"
        if(banana) result += " 바나나"
        if(orange) result += " 오렌지"

        textView.text = "${result}가 선택되었습니다."
    }
}
