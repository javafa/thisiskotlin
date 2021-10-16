package com.example.thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var wThread = WorkerThread()
//        wThread.start()

        var rThread = Thread(WorkerRunnable())
        rThread.start()

        Thread {
            var i = 0
            while (i < 10) {
                i += 1
                Log.i("LambdaThread", "$i")
            }
        }.start()
        thread(start=true) {
            var i = 0
            while (i < 10) {
                i += 1
                Log.i("KotlinThread", "$i")
            }
        }
    }
}

class WorkerThread : Thread() {
    override fun run() {
        var i = 0
        while (i < 10) {
            i += 1
            Log.i("WorkerThread", "$i")
        }
    }
}

class WorkerRunnable : Runnable {
    override fun run() {
        var i = 0
        while (i < 10) {
            i += 1
            Log.i("WorkerRunnable", "$i")
        }
    }
}