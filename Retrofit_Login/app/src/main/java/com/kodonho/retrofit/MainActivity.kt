package com.kodonho.retrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class MainActivity : AppCompatActivity() {

    lateinit var id:EditText
    lateinit var password:EditText
    lateinit var button: Button
    lateinit var btnRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        id = findViewById(R.id.textId)
        password = findViewById(R.id.textPassword)
        button = findViewById(R.id.button)
        btnRegister = findViewById(R.id.btnRegister)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://3.34.141.115") // 주소는 본인의 서버 주소로 설정
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SignService::class.java)

        button.setOnClickListener {
            val idStr = id.text.toString()
            val pwStr = password.text.toString()
            service.login(idStr, pwStr).enqueue(object :Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val result = response.body()
                    Log.d("로그인", "${result}")
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("로그인", "${t.localizedMessage}")
                }
            })
        }

        btnRegister.setOnClickListener {
            Intent(this, RegisterActivity::class.java).run {
                startActivity(this)
            }
        }
    }
}

interface SignService {
    @FormUrlEncoded
    @POST("login.php")
    fun login(@Field("id") id:String, @Field("password") pw:String) : Call<LoginResponse>
}