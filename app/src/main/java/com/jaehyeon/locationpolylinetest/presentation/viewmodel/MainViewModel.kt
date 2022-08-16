package com.jaehyeon.locationpolylinetest.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.jaehyeon.locationpolylinetest.domain.LocationRepository
import com.jaehyeon.locationpolylinetest.utils.ListLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Jaehyeon on 2022/08/16.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val tracker: LocationRepository
): ViewModel(){

    private val _location = ListLiveData<Location>()
    val location: LiveData<ArrayList<Location>> get() = _location

    private val _latLng = ListLiveData<LatLng>()
    val latLng: LiveData<ArrayList<LatLng>> get() = _latLng

    private var count = 0

    fun getLocation() {
        viewModelScope.launch {
            while(true) {
                tracker.getCurrentLocation()?.let { location ->
                    withContext(Dispatchers.Main) {
                        _location.add(location)
                    }
                }
                delay(1500L)
            }
        }
    }

    fun setLatLng(value: LatLng) {
        // test code
//        when (++count % 5) {
//            0 -> {
//                _latLng.add(value)
//            }
//
//            1 -> {
//                _latLng.add(LatLng(36.7949, 127.1085))
//            }
//
//            2 -> {
//                _latLng.add(LatLng(36.79498, 127.1090))
//            }
//
//            3 -> {
//                _latLng.add(LatLng(36.7952, 127.1095))
//            }
//
//            4 -> {
//                _latLng.add(LatLng(36.7955, 127.1100))
//            }
//        }
        _latLng.add(value)
    }
}