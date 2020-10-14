package com.kotlin.demo.model

data class LoginModel(
    val result: String,
    val `data`: Data
) {
    data class Data(
        val userName: String
    )
}
