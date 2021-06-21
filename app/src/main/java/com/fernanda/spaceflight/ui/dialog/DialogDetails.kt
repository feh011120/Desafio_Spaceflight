package com.fernanda.spaceflight.ui.dialog

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.fernanda.spaceflight.R
import com.fernanda.spaceflight.databinding.FragmentDetailsBinding
import com.fernanda.spaceflight.network.response.NewResponse
import org.koin.android.viewmodel.ext.android.viewModel

class
DialogDetails : DialogFragment(), DialogListener {

    lateinit var binding: FragmentDetailsBinding
    private val viewModel: DialogDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.listener = this
        viewModel.getClick()

        viewModel.news.observe(requireActivity(), Observer<NewResponse> {
            binding.textViewTitle.text = it.title
            binding.textViewSumary.text = it.summary

        })
    }

    override fun onClose() {
        dialog?.dismiss()
    }

    override fun onNavigate(url: String) {
        try {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(myIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Não foi possivel carregar"
                    + "Instale o navegador", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}