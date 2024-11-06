package com.example.tp_mobile.views.fireball

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tp_mobile.R
import com.example.tp_mobile.databinding.FragmentFireballViewBinding
import com.example.tp_mobile.model.Fireball
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.pow
import kotlin.math.roundToInt

class FireballViewFragment : Fragment() {

    private lateinit var binding: FragmentFireballViewBinding
    private lateinit var viewModel: FireballListViewModel
    var fireball: Fireball? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_fireball_view, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[FireballListViewModel::class.java]

        arguments?.let { bundle ->
            fireball = bundle.getSerializable("fireball") as? Fireball
            fireball?.let {
                updateFireballView()
            } ?: run {
            }
        }

        binding.mapButton.setOnClickListener {
            mapButtonClicked()
        }

        binding.favIcon.setOnClickListener {
            favButtonClicked()
        }

        viewModel.isFavorite(fireball!!).observe(viewLifecycleOwner) {
            if (it) {
                binding.favIcon.setImageResource(R.drawable.baseline_favorite_border_24)
                binding.favIcon.setColorFilter(R.color.black)
            } else {
                binding.favIcon.setImageResource(R.drawable.baseline_favorite_24)
                binding.favIcon.setColorFilter(R.color.red)
            }
        }

        setUpNavigation()
    }

    private fun updateFireballView() {
        binding.reportDate.text = "Report date : \n${formatDate(fireball?.date ?: "")}"
        binding.radiatedValue.text = "${fireball?.energy ?: "?"}e10 J"
        binding.impactValue.text = "${fireball?.impactE ?: "?"}kt"
        binding.altitudeValue.text = "${fireball?.alt ?: "?"} m"
        binding.radiusValue.text = "${
            calculateCraterRadius(
                fireball?.impactE ?: 0.0,
                fireball?.alt ?: 0.0,
                fireball?.energy ?: 0.0,
                fireball?.vel?.toDouble() ?: 0.0
            )
        } m"
        binding.coordinatesValue.text =
            "${fireball?.lon ?: "?"} ${fireball?.lonDir ?: "?"} \n${fireball?.lat ?: "?"} ${fireball?.latDir ?: "?"}"
        binding.speedValue.text = "${fireball?.vel ?: "?"} km/s"
    }

    private fun calculateCraterRadius(
        impactEnergy: Double,
        altitude: Double,
        radiatedEnergy: Double,
        speed: Double
    ): Double {
        val result = 0.05 * (impactEnergy / 4.184 * 10.0.pow(12).pow(0.33) )
        return BigDecimal(result).setScale(2, RoundingMode.HALF_UP).toDouble()

        //R=0,05× Energie en J / 4,184×10^12 * 0,33

    }

    fun formatDate(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

        val zonedDateTime = ZonedDateTime.parse(dateString, inputFormatter)

        return zonedDateTime.format(outputFormatter)
    }

    fun mapButtonClicked() {
        val parentFragmentTransaction = parentFragmentManager.beginTransaction()
        parentFragmentTransaction.replace(
            R.id.fireballFragmentContainer,
            FireballMapFragment.newInstance(fireball)
        )
        parentFragmentTransaction.commit()
    }

    fun favButtonClicked() {
        viewModel.isFavorite(fireball!!).observe(viewLifecycleOwner) {
            if (it) {
                viewModel.removeFavorite(fireball!!)
                fireball!!.isFavorite = false
                binding.favIcon.setImageResource(R.drawable.baseline_favorite_border_24)
                binding.favIcon.setColorFilter(R.color.black)
            } else {
                viewModel.addFavorite(fireball!!)
                fireball!!.isFavorite = true
                binding.favIcon.setImageResource(R.drawable.baseline_favorite_24)
                binding.favIcon.setColorFilter(R.color.red)
            }
        }
    }

    private fun setUpNavigation(){
        requireActivity().findViewById<ImageButton>(R.id.back).setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.fireballFragmentContainer, FireballListFragment.newInstance())
                .commit()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fireballFragmentContainer, FireballListFragment.newInstance())
                .commit()
        }
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
