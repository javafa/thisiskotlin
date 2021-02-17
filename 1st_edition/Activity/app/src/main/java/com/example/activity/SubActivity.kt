package com.example.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sub.*

class SubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        setContentView(R.layout.activity_sub)
        to1.text = intent.getStringExtra("from1")
        to2.text = "${intent.getIntExtra("from2",0)}"
        btnClose.setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putExtra("returnValue", editMessage.text.toString())
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}
