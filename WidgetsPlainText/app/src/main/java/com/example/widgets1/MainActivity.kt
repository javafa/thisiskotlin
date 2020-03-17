package com.example.widgets1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText.addTextChangedListener(object:TextWatcher{

            override fun afterTextChanged(p0: Editable?) {
                Log.d("EditText", "현재 입력된 값=${p0.toString()}")

                if(p0.toString().length >= 8) {
                    Log.d("EditText", "8자 이상일 때만 출력=${p0.toString()}")
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }
}
