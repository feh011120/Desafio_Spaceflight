package com.fernanda.spaceflight.repository

import com.fernanda.spaceflight.network.ApiService
import com.fernanda.spaceflight.network.response.NewResponse
import com.orhanobut.hawk.Hawk

class NewRepository(private val apiService: ApiService) {
    suspend fun getNews(limit: Int, start: Int) = apiService.getNews(limit, start).await()

    fun saveNews(news: List<NewResponse?>) {
        Hawk.put("news", news)
    }

    fun getNews(): List<NewResponse> {
        return Hawk.get("news")
    }

    fun saveClick(newResponse: NewResponse) {
        Hawk.put("Click", newResponse)
    }

    fun getClick(): NewResponse {
        return Hawk.get("Click")
    }

}