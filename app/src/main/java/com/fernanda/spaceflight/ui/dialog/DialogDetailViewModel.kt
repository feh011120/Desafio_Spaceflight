package com.fernanda.spaceflight.ui.dialog

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fernanda.spaceflight.network.response.NewResponse
import com.fernanda.spaceflight.repository.NewRepository

class DialogDetailViewModel(private val repository: NewRepository) : ViewModel() {
    var listener: DialogListener? = null

    private val _new = MutableLiveData<NewResponse>()
    val news: LiveData<NewResponse>
        get() = _new

    fun getClick() {
        _new.postValue(repository.getClick())
    }

    fun onClose(view: View) {
        listener!!.onClose()
    }

    fun onNavigate(view: View) {
        listener!!.onNavigate(repository.getClick()
                .url
                ?: "https://www.spaceflightnewsapi.net/documentation#/Article/get_api_v2_articles")
    }
}