package com.example.tp_mobile.views.fireball

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mobile.R
import com.example.tp_mobile.model.domain.api.FireballApiController
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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


        lifecycleScope.launch {
            var i = 0
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while(true) {
                    viewModel.fetchFireballDataAdd(2,i*2)
                    delay(1000)
                    i++
                }
            }
        }

        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter = CustomFireballAdapter(onCLick = { fireball ->
                viewModel.selectedFireball.value = fireball
                val parentFragmentTransaction = parentFragmentManager.beginTransaction()
                parentFragmentTransaction.replace(R.id.fireballFragmentContainer, FireballViewFragment.newInstance())
                parentFragmentTransaction.commit()
            }, items)
            recyclerView.adapter = adapter
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