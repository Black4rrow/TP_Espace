package com.example.tp_mobile.views.fireball

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.example.Fireball
import com.example.tp_mobile.R

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

        arguments?.let {
            fireball = it.getSerializable("fireball") as Fireball
            updateFireballView()
        }

    }

    private fun updateFireballView() {

    }

    companion object {
        @JvmStatic
        fun newInstance(fireball: Fireball) =
            FireballViewFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("fireball", fireball)
                }
            }
    }
}
