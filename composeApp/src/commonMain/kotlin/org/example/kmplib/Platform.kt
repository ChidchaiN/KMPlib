package org.example.kmplib

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform