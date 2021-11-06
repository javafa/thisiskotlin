package net.flow9.thisiskotlin.restapi

import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @GET("user/list")
    fun getUsers(): Call<UserResponse>

    @DELETE("user/{userId}")
    fun deleteUser(@Path("userId") userId:Int): Call<UserResponse>

    @PUT("user")
    fun putUser(@Body user:User): Call<UserResponse>

    @POST("user")
    fun postUser(@Body user:User): Call<UserResponse>
}