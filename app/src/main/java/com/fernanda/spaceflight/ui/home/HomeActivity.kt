package com.fernanda.spaceflight.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fernanda.spaceflight.R
import com.fernanda.spaceflight.databinding.ActivityHomeBinding
import com.fernanda.spaceflight.ui.dialog.error.DialogError
import com.fernanda.spaceflight.ui.fragment.NewsFragment
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), HomeListener {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initViewModel()
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        viewModel.listener = this
        viewModel.getNews()
    }

    override fun apiSuccess() {
        val frameLayout = NewsFragment()
        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.container, frameLayout)
        transition.commit()
    }

    override fun apiError(message: String) {
        DialogError().show(supportFragmentManager, "Error")
    }
}