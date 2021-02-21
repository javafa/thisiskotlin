package kr.co.hanbit.miniquiz4_2_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread(start=true) {
            for (i in 0 until 100) {
                Thread.sleep(1000)
                runOnUiThread {
                    textView.text = "${i+1}"
                }
            }
        }
    }
}
