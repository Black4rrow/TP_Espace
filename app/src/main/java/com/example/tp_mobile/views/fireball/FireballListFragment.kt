package com.example.tp_mobile.views.fireball

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mobile.R
import com.example.tp_mobile.model.domain.api.FireballApiController
import com.example.tp_mobile.utils.SortStyle

class FireballListFragment : Fragment() {

    private lateinit var viewModel: FireballListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomFireballAdapter
    private var sortingStyle: SortStyle? = null


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

        view.findViewById<ImageButton>(R.id.sortButton).setOnClickListener {
            showSortDialog()
        }


        viewModel.fetchFireballDataAdd(20, 0, null)
    }

    private fun showSortDialog() {
        // Charger le layout personnalisé
        val dialogView = LayoutInflater.from(context).inflate(R.layout.popup_list_sort, null)

        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.radioGroup)
        val closeButton = dialogView.findViewById<ImageButton>(R.id.close_button)
        val confirmButton = dialogView.findViewById<Button>(R.id.confirm_button)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setOnShowListener {
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val selectedRadioButton = group.findViewById<RadioButton>(checkedId)
            }

            confirmButton.setOnClickListener {
                dialog.dismiss()
                val selectedId = radioGroup.checkedRadioButtonId

                when (selectedId) {
                    R.id.radioNone -> {
                        sortFireballs(SortStyle.NONE)
                    }

                    R.id.radioDateAsc -> {
                        sortFireballs(SortStyle.DATE_ASC)
                    }

                    R.id.radioDateDesc -> {
                        sortFireballs(SortStyle.DATE_DESC)
                    }

                    R.id.radioSpeedAsc -> {
                        sortFireballs(SortStyle.SPEED_ASC)
                    }

                    R.id.radioSpeedDesc -> {
                        sortFireballs(SortStyle.SPEED_DESC)
                    }

                    R.id.radioEnergyAsc -> {
                        sortFireballs(SortStyle.RADIATED_ENERGY_ASC)
                    }

                    R.id.radioEnergyDesc -> {
                        sortFireballs(SortStyle.RADIATED_ENERGY_DESC)
                    }

                    R.id.radioFavorites -> {
                        sortFireballs(SortStyle.FAVORITES)
                    }
                }
            }
        }

        dialog.show()
    }

    fun sortFireballs(sortStyle: SortStyle) {
        sortingStyle = sortStyle
        viewModel.fetchFireballData(20, 0, sortingStyle)
    }

    private fun setUpListener() {
        recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (!recyclerView.canScrollVertically(1)) {
                viewModel.fetchFireballDataAdd(20, viewModel.items.value?.size ?: 0, sortingStyle)
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