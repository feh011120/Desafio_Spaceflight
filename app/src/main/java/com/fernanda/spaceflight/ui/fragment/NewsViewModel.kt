package com.fernanda.spaceflight.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fernanda.spaceflight.network.response.NewResponse
import com.fernanda.spaceflight.repository.NewRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NewsViewModel(private val repository: NewRepository) : ViewModel(), CoroutineScope {

    var listener: NewsListener? = null

    private val _newsList = MutableLiveData<List<NewResponse>>()
    val newList: LiveData<List<NewResponse>>
        get() = _newsList

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job


    fun initViewModel() {
        _newsList.postValue(repository.getNews())

        listener!!.onSearch()
    }

    fun saveClick(newResponse: NewResponse) {
        repository.saveClick(newResponse)
    }

    fun getNewsPage(num: Int) {
        launch {
            try {
                val response = repository.getNews(15, num)
                if (response.isSuccessful) {
                    val listApi = response.body()!!
                    val listCache = repository.getNews()
                    val listActual = arrayListOf<NewResponse>()

                    listActual.addAll(listCache)
                    listActual.addAll(listApi)

                    _newsList.postValue(listActual)
                }
            } catch (e: Exception) {
                _newsList.postValue(arrayListOf())

            }
        }
    }

}