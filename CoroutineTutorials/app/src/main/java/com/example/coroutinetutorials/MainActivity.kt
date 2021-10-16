package com.example.coroutinetutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinetutorials.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    var job:Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnJobStart.setOnClickListener {
            job = CoroutineScope(Dispatchers.Default).launch() {
                val job1 = launch {
                    for(i in 0..10) {
                        delay(500)
                        Log.d("코루틴", "결과 = $i")
                    }
                }
            }
        }

        binding.btnJobStop.setOnClickListener {
            job?.cancel()
        }

        binding.btnJobJoin.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch() {
                launch {
                    for(i in 0..5) {
                        delay(500)
                        Log.d("코루틴", "결과1 = $i")
                    }
                }.join()

                launch {
                    for(i in 0..5) {
                        delay(500)
                        Log.d("코루틴", "결과2 = $i")
                    }
                }
            }
        }

        binding.btnAsync.setOnClickListener {
            CoroutineScope(Dispatchers.Default).async {
                val deffered1 = async {
                    delay(500)
                    350
                }
                val deffered2 = async {
                    delay(1000)
                    200
                }
                Log.d("코루틴", "연산 결과 = ${deffered1.await() + deffered2.await()}")
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            // 화면 처리
            val result = withContext(Dispatchers.IO) {
                readFile()
            }
            Log.d("코루틴", "파일결과=$result")
        }
    }
    suspend fun readFile() : String {
        return "파일내용"
    }
}