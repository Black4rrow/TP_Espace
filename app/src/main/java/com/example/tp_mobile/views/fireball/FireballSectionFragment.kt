package com.example.tp_mobile.views.fireball

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mobile.MainActivity
import com.example.tp_mobile.R
import com.example.tp_mobile.utils.SortStyle


class FireballSectionFragment : Fragment() {

    private lateinit var viewModel: FireballListViewModel

    private lateinit var powerRecyclerView: RecyclerView
    private lateinit var powerAdapter: PowerAdapter

    private lateinit var dateRecyclerView: RecyclerView
    private lateinit var dateAdapter: PowerAdapter

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

        powerRecyclerView = view.findViewById(R.id.powerRecyclerView)
        dateRecyclerView = view.findViewById(R.id.dateRecyclerView)


        view.findViewById<Button>(R.id.listButton).setOnClickListener {
            val parentFragmentTransaction = parentFragmentManager.beginTransaction()
            parentFragmentTransaction.replace(
                R.id.fireballFragmentContainer,
                FireballListFragment.newInstance()
            )
            parentFragmentTransaction.commit()

        }


        requireActivity().findViewById<ImageButton>(R.id.back).setOnClickListener{
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(0,0)
            requireActivity().finish()
            requireActivity().overridePendingTransition(0,0)
        }


    }
    fun getThreeBestFireballs(sortStyle: SortStyle){
        return viewModel.fetchFireballData(3, 0, sortStyle)
    }




}