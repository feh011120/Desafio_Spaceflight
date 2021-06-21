package com.fernanda.spaceflight.ui.dialog.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.fernanda.spaceflight.R
import com.fernanda.spaceflight.databinding.FragmentErrorLoadingBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DialogError : DialogFragment(), DialogErrorListener {

    lateinit var binding: FragmentErrorLoadingBinding
    private val viewModel: DialogErrorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentErrorLoadingBinding.inflate(inflater, container, false)

        binding.viewModell = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.listener = this

    }

    override fun onClose() {
        dialog?.dismiss()
    }


}