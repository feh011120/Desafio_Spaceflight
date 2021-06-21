package com.fernanda.spaceflight.ui.home

interface HomeListener {
    fun apiSuccess()
    fun apiError(message: String)
}