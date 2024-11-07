package com.example.tp_mobile.views.fireball

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mobile.R
import com.example.tp_mobile.model.Fireball
import com.example.tp_mobile.model.OnFireballFavoriteListener
import com.example.tp_mobile.model.domain.api.FireballApiController
import com.example.tp_mobile.utils.SortStyle
import com.google.android.material.materialswitch.MaterialSwitch
import kotlinx.coroutines.launch

class FireballListFragment : Fragment(), OnFireballFavoriteListener {

    private lateinit var viewModel: FireballListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomFireballAdapter
    private lateinit var favSwitch: MaterialSwitch
    private lateinit var favIndicator: ImageView
    private var sortingStyle: SortStyle? = null
    private var temporaryData = mutableListOf<Fireball>()


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
        favSwitch = view.findViewById(R.id.favoriteSwitch)
        favIndicator = view.findViewById(R.id.favorite_indicator)

        setUpObserver()
        setUpListener()

        view.findViewById<ImageButton>(R.id.sortButton).setOnClickListener {
            showSortDialog()
        }

        setUpNavigation()


        viewModel.fetchFireballData(20, 0, null)
    }

    private fun showSortDialog() {
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
                }
            }
        }

        dialog.show()
    }

    fun sortFireballs(sortStyle: SortStyle) {
        sortingStyle = sortStyle
        adapter.sortData(sortStyle)
    }

    fun toggleFavorites(showFavorites: Boolean){
        if(showFavorites){
            viewModel.getFavorites().observe(viewLifecycleOwner){
                for (fireball in it){
                    fireball.isFavorite = true
                }
                temporaryData = adapter.getData().toMutableList()
                adapter.replaceAllData(it.toMutableList())
                adapter.sortData(sortingStyle ?: SortStyle.NONE)
            }

            favIndicator.setImageResource(R.drawable.baseline_favorite_24)
            favIndicator.setColorFilter(R.color.red)
        }else{
            adapter.replaceAllData(temporaryData.toMutableList())
            adapter.sortData(sortingStyle ?: SortStyle.NONE)

            favIndicator.setImageResource(R.drawable.baseline_favorite_border_24)
            favIndicator.setColorFilter(R.color.black)
        }
    }

    override fun onFavoriteClicked(fireball: Fireball, holder: CustomFireballAdapter.FireballViewHolder, position: Int) {
        viewModel.isFavorite(fireball).observe( viewLifecycleOwner){
            if (it) {
                viewModel.removeFavorite(fireball)
                fireball.isFavorite = false
            } else {
                viewModel.addFavorite(fireball)
                fireball.isFavorite = true
            }

            adapter.notifyItemChanged(position)
        }

    }


    private fun setUpListener() {
        favSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            toggleFavorites(isChecked)
        }

        recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(!favSwitch.isChecked){
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.fetchFireballDataAdd(20, viewModel.items.value?.size ?: 0, sortingStyle)
                }
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
        }, fireballs, this)

        recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.replaceAllData(items.toMutableList())
        }
    }

    private fun setUpNavigation(){
        requireActivity().findViewById<ImageButton>(R.id.back).setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.fireballFragmentContainer, FireballSectionFragment.newInstance())
                .commit()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fireballFragmentContainer, FireballSectionFragment.newInstance())
                .commit()
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