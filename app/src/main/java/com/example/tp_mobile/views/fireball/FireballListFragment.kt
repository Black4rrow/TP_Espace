package com.example.tp_mobile.views.fireball

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mobile.R
import com.example.tp_mobile.model.domain.api.FireballApiController

class FireballListFragment : Fragment() {

    private lateinit var viewModel: FireballListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomFireballAdapter

    private val fireballApiController = FireballApiController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fireball_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[FireballListViewModel::class.java]


        recyclerView = view.findViewById(R.id.listView)



        setUpObserver()
        setUpListener()
        viewModel.fetchFireballDataAdd(20, 0)
    }

    private fun setUpListener() {
        recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (!recyclerView.canScrollVertically(1)) {
                viewModel.fetchFireballDataAdd(20, viewModel.items.value?.size ?: 0)
            }
        }
    }

    private fun setUpObserver() {

        var fireballs = viewModel.items.value ?: emptyList()
        fireballs = fireballs.toMutableList()

        adapter = CustomFireballAdapter(onCLick = { fireball ->
            val parentFragmentTransaction = parentFragmentManager.beginTransaction()
            parentFragmentTransaction.replace(
                R.id.fireballFragmentContainer,
                FireballViewFragment.newInstance(fireball)
            )
            parentFragmentTransaction.commit()
        }, fireballs)

        recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.replaceAllData(items.toMutableList())
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            FireballListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}