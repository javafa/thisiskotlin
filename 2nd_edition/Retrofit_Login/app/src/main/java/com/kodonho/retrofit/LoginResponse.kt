package com.kodonho.retrofit

data class LoginResponse(
    val email: Int,
    val id: Int,
    val name: String,
    val password: String,
    val phonenum: String,
    val seq: String,
    val success: Boolean
)