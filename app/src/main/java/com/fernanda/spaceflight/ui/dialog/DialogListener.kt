package com.fernanda.spaceflight.ui.dialog

interface DialogListener {
    fun onClose()
    fun onNavigate(url: String)
}