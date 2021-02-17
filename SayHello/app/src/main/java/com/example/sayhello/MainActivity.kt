package com.example.sayhello

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sayhello.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.btnSay.setOnClickListener{
            binding.textSay.setText("Hello Kotlin!!!")
        }
    }
}
