package net.flow9.thisiskotlin.restapi

data class UserResponse(
    val `data`: List<User>,
    val message: String,
    val result: Boolean
)