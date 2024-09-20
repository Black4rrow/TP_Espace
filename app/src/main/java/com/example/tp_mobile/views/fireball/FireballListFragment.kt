package com.example.tp_mobile.views.fireball

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.example.Fireball
import com.example.tp_mobile.R
import com.example.tp_mobile.model.retrofit.FireballApiController
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray.Companion.serializer

class FireballListFragment : Fragment() {

    private lateinit var viewModel: FireballListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomFireballAdapter

    private val fireballApiController = FireballApiController()

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

        suspend {
            fireballApiController.fireballApiService.getFireballData(20, 0)
        }

        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter = CustomFireballAdapter(onCLick = { fireball ->
                findNavController().navigate(
                    FireballListFragmentDirections.goToFireballView(fireball)
                )
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