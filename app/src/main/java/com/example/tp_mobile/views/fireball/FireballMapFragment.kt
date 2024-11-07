package com.example.tp_mobile.views.fireball

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.tp_mobile.R
import com.example.tp_mobile.model.Fireball
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class FireballMapFragment : Fragment() {

    var fireball: Fireball? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fireball_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        // On verifie si la carte est déjà prête
        mapFragment?.getMapAsync { googleMap ->
            setupMap(googleMap)
        }

        setUpNavigation()
    }

    private fun setupMap(googleMap: GoogleMap) {
        fireball?.let {
            Log.e("nathan", "setupMap: ${it.lat}, ${it.lon}")
            val fireballLocation = LatLng(it.lat.toDouble(), it.lon.toDouble())
            googleMap.addMarker(MarkerOptions().position(fireballLocation).title("Marker at fireball of date: ${it.date}"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(fireballLocation))
        }
    }

    private fun setUpNavigation(){
        requireActivity().findViewById<ImageButton>(R.id.back).setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.fireballFragmentContainer, FireballViewFragment.newInstance(fireball!!))
                .commit()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fireballFragmentContainer, FireballViewFragment.newInstance(fireball!!))
                .commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(fireball: Fireball?) =
            FireballMapFragment().apply {
                this.fireball = fireball
                arguments = Bundle().apply {
                    putSerializable("fireball", fireball)
                }
            }
    }
}
