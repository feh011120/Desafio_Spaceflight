package com.fernanda.spaceflight.repository

import com.fernanda.spaceflight.network.ApiService

class NewsRepository(private val apiService: ApiService){
    suspend fun getNews(limit: Int, start: Int) = apiService.getNews(limit, start)
}