package com.example.networkretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networkretrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity: AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = CustomAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        binding.buttonRequest.setOnClickListener {
            val githubService = retrofit.create(GithubService::class.java)
            githubService.users().enqueue(object: Callback<Repository> {
                override fun onFailure(call: Call<Repository>, t: Throwable) {
                    /* */
                }
                override fun onResponse(
                        call: Call<Repository>,
                        response: Response<Repository>
                ) {
                    adapter.userList = response.body() as Repository
                    adapter.notifyDataSetChanged()
                }
            })
        }
    }
}

interface GithubService {
    @GET("users/Kotlin/repos")
    fun users(): Call<Repository>
}
