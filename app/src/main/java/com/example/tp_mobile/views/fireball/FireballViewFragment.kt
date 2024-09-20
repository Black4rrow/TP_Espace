package com.example.tp_mobile.views.fireball

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tp_mobile.R
import androidx.navigation.fragment.navArgs

class FireballViewFragment : Fragment() {

    private val navArgs: FireballViewFragmentArgs by navArgs()

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

        Log.e("Fireball", navArgs.fireball.toString())
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
