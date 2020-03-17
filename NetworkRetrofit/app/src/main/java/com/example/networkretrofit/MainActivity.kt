package com.example.networkretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CustomAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        buttonRequest.setOnClickListener {
            val githubService = retrofit.create(GithubService::class.java)
            githubService.users().enqueue(object: Callback<List<Repository>> {
                override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                    /* */
                }
                override fun onResponse(
                    call: Call<List<Repository>>,
                    response: Response<List<Repository>>
                ) {
                    adapter.userList.addAll(response.body() as List<Repository>)
                    adapter.notifyDataSetChanged()
                }
            })
        }
    }
}

interface GithubService {
    @GET("users/Kotlin/repos")
    fun users() : Call<List<Repository>>
}