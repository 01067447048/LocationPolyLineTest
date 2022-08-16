package com.jaehyeon.locationpolylinetest

import android.Manifest
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.addPolyline
import com.google.maps.android.ktx.awaitMap
import com.jaehyeon.locationpolylinetest.databinding.ActivityMainBinding
import com.jaehyeon.locationpolylinetest.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()
    private lateinit var mMap: GoogleMap
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            model.getLocation()
        }
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))

        lifecycleScope.launchWhenCreated {
            val mapFragment: SupportMapFragment? =
                supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
            mMap = mapFragment?.awaitMap() ?: throw Throwable("슬프다.")
            mMap.apply {
                setMinZoomPreference(15f)
                setMaxZoomPreference(20f)
            }

            model.location.observe(this@MainActivity) { locations ->
                if (locations.isNotEmpty()){
                    LatLng(locations.last().latitude, locations.last().longitude).also {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
                        model.setLatLng(it)
                    }
                }
            }
            
        }

        lifecycleScope.launchWhenStarted {
            model.latLng.observe(this@MainActivity) { latlngs ->
                if (latlngs.size > 0) {
                    mMap.addPolyline {
                        addAll(latlngs)
                        color(Color.RED)
                    }
                    mMap.addMarker {
                        position(latlngs.last())
                    }
                }
            }
        }

    }

}