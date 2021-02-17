package com.example.asynctask

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonDownload.setOnClickListener {
            val asyncTask = object : AsyncTask<String, Void, Bitmap?>() {
                override fun doInBackground(vararg params: String?): Bitmap? {
                    // 04 코드는 여기에 작성됩니다.
                    val urlString = params[0]!!
                    try {
                        val url = URL(urlString)
                        val stream = url.openStream()
                        return BitmapFactory.decodeStream(stream)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        return null
                    }
                }
                override fun onProgressUpdate(vararg values: Void?) {
                    super.onProgressUpdate(*values)
                }
                override fun onPostExecute(result: Bitmap?) {
                    // 05 코드는 여기에 작성됩니다.
                    if (result != null) {
                        imagePreview.setImageBitmap(result)
                    } else {
                        Toast.makeText(this@MainActivity, "다운로드 오류", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //03 코드는 여기에 작성됩니다.
            asyncTask?.execute(editUrl.text.toString())
        }
    }
}
