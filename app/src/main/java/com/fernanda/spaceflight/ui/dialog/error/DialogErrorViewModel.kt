package com.fernanda.spaceflight.ui.dialog.error

import android.view.View
import androidx.lifecycle.ViewModel
import com.fernanda.spaceflight.repository.NewRepository

class DialogErrorViewModel(private val repository: NewRepository) : ViewModel() {
    var listener: DialogErrorListener? = null

    fun onClose(view: View) {
        listener!!.onClose()
    }
}