package kr.co.hanbit.widgetsprogressbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kr.co.hanbit.widgetsprogressbar.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        thread(start=true) {
            Thread.sleep(3000)
            runOnUiThread {
                showProgress(false)
            }
        }
    }

    fun showProgress(show: Boolean) {
        binding.progressLayout.visibility = if(show) View.VISIBLE else View.GONE
    }
}