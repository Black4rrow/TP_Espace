package com.example.tp_mobile.views.fireball

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mobile.MainActivity
import com.example.tp_mobile.R
import com.example.tp_mobile.utils.SortStyle
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class FireballSectionFragment : Fragment() {

    private val viewModel: FireballListViewModel by viewModels()

    private lateinit var dateRecyclerView: RecyclerView
    private lateinit var powerRecyclerView: RecyclerView
    private lateinit var powerAdapter: PowerAdapter
    private lateinit var dateAdapter: DateAdapter

    private lateinit var barPowerText: TextView
    private lateinit var barPowerText2: TextView
    private lateinit var barPowerText3: TextView

    private lateinit var dateText: TextView
    private lateinit var dateText2: TextView
    private lateinit var dateText3: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fireball_section, container, false)
    }

    @SuppressLint("SetTextI18n", "CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        powerRecyclerView = view.findViewById(R.id.powerRecyclerView)
        powerRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        dateRecyclerView = view.findViewById(R.id.dateRecyclerView)
        dateRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        barPowerText = view.findViewById(R.id.bar_power_text)
        barPowerText2 = view.findViewById(R.id.bar_power_text2)
        barPowerText3 = view.findViewById(R.id.bar_power_text3)

        dateText = view.findViewById(R.id.last_one_date_text)
        dateText2 = view.findViewById(R.id.last_one_date_text2)
        dateText3 = view.findViewById(R.id.last_one_date_text3)





        powerAdapter = PowerAdapter(
            onClick = { fireball ->
                val parentFragmentTransaction = parentFragmentManager.beginTransaction()
                parentFragmentTransaction.replace(
                    R.id.fireballFragmentContainer,
                    FireballViewFragment.newInstance(fireball)
                )
                parentFragmentTransaction.commit()
            },
            itemList = emptyList()
        )
        powerRecyclerView.adapter = powerAdapter


        dateAdapter = DateAdapter(onClick = { fireball ->
            val parentFragmentTransaction = parentFragmentManager.beginTransaction()
            parentFragmentTransaction.replace(
                R.id.fireballFragmentContainer,
                FireballViewFragment.newInstance(fireball)
            )
            parentFragmentTransaction.commit()
        },
            itemList = emptyList()
        )
        dateRecyclerView.adapter = dateAdapter


        viewModel.items.observe(viewLifecycleOwner) { fireballList ->
            if (fireballList.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Aucune Fireball trouvée", Toast.LENGTH_SHORT).show()
                powerAdapter.updateData(emptyList())
            } else {
                powerAdapter.updateData(fireballList)

                fireballList.getOrNull(0)?.let {
                    barPowerText.text = "${it.energy}e10 J"
                }
                fireballList.getOrNull(1)?.let {
                    barPowerText2.text = "${it.energy}e10 J"
                }
                fireballList.getOrNull(2)?.let {
                    barPowerText3.text = "${it.energy}e10 J"
                }
            }
        }

        viewModel.items.observe(viewLifecycleOwner) { fireballList ->
            if (fireballList.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Aucune Fireball trouvée", Toast.LENGTH_SHORT).show()
                dateAdapter.updateData(emptyList())
            } else {
                dateAdapter.updateData(fireballList)

                fireballList.getOrNull(0)?.let {
                    dateText.text = "${it.date} date"
                }
                fireballList.getOrNull(1)?.let {
                    dateText2.text = "${it.date} "
                }
                fireballList.getOrNull(2)?.let {
                    dateText3.text = "${it.date} "
                }
            }
        }


        viewModel.fetchFireballData(3, 0, SortStyle.DATE_DESC)
        viewModel.items.observe(viewLifecycleOwner) { fireballList ->
            if (fireballList.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Aucune Fireball trouvée", Toast.LENGTH_SHORT).show()
            } else {
                fireballList.getOrNull(0)?.let {
                    dateText.text = it.date?.let { it1 -> formatDate(it1) }
                }
                fireballList.getOrNull(1)?.let {
                    dateText2.text = it.date?.let { it1 -> formatDate(it1) }
                }
                fireballList.getOrNull(2)?.let {
                    dateText3.text = it.date?.let { it1 -> formatDate(it1) }
                }
            }
        }
        view.findViewById<Button>(R.id.listButton).setOnClickListener {
            val parentFragmentTransaction = parentFragmentManager.beginTransaction()
            parentFragmentTransaction.replace(
                R.id.fireballFragmentContainer,
                FireballListFragment.newInstance()
            )
            parentFragmentTransaction.commit()
        }

        requireActivity().findViewById<ImageButton>(R.id.back).setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(0, 0)
            requireActivity().finish()
            requireActivity().overridePendingTransition(0, 0)
        }
    }
    
    companion object {
        @JvmStatic
        fun newInstance() =
            FireballSectionFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
    private fun formatDate(dateString: String): String {
        return try {
            val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val zonedDateTime = ZonedDateTime.parse(dateString, inputFormatter)
            zonedDateTime.format(outputFormatter)
        } catch (e: Exception) {
            "Invalid Date"
        }
    }
}
