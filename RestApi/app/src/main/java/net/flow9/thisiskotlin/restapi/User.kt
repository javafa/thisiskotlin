package net.flow9.thisiskotlin.restapi

data class User(
    val address: String,
    val age: Int,
    val created_at: String,
    val email: String,
    val gender: String,
    val id: Int,
    val name: String,
    val tel: String,
    val updated_at: String
)