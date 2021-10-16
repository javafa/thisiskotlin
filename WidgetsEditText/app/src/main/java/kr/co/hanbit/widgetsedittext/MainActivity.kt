package kr.co.hanbit.widgetsedittext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.widget.addTextChangedListener
import kr.co.hanbit.widgetsedittext.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.editText.addTextChangedListener {
            if (it.toString().length >= 8) {
                Log.d("EditText", "8자 이상일 때만 출력=${it.toString()}")
            }
        }
    }
}