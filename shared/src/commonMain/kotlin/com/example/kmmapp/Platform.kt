package com.example.kmmapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform