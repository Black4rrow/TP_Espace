package com.example.tp_mobile.views.fireball

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.tp_mobile.R
import androidx.navigation.fragment.navArgs
import com.example.example.Fireball

class FireballViewFragment : Fragment() {

    private lateinit var viewModel: FireballListViewModel
    private var fireball: Fireball? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fireball_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[FireballListViewModel::class.java]
        fireball = viewModel.selectedFireball.value
        viewModel.selectedFireball.observe(viewLifecycleOwner) {
            fireball = it
            updateFireballView()
        }
    }

    private fun updateFireballView() {

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FireballViewFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
