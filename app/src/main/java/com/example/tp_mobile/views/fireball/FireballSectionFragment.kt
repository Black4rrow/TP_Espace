package com.example.tp_mobile.views.fireball

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.tp_mobile.R


class FireballSectionFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fireball_section, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FireballSectionFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.listButton).setOnClickListener {
            val parentFragmentTransaction = parentFragmentManager.beginTransaction()
            parentFragmentTransaction.replace(
                R.id.fireballFragmentContainer,
                FireballListFragment.newInstance()
            )
            parentFragmentTransaction.commit()
        }

    }

}