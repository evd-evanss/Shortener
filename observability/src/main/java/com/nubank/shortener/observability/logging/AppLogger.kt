package com.nubank.shortener.observability.logging

interface AppLogger {
    fun info(message: String)
    fun error(message: String, throwable: Throwable? = null)
}