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

    // 지속적으로 받아오는 위치 정보를 List로 관리.
    private val _location = ListLiveData<Location>()
    val location: LiveData<ArrayList<Location>> get() = _location

    // Location 을 Polyline을 그리기 위해 LatLng 로 바꿔 관리.
    private val _latLng = ListLiveData<LatLng>()
    val latLng: LiveData<ArrayList<LatLng>> get() = _latLng

    //위치 정보를 받는 interval
    private val interval = 1500L

    //Test 용도
    private var count = 0

    fun getLocation() {
        viewModelScope.launch {
            while(true) {
                tracker.getCurrentLocation()?.let { location ->
                    withContext(Dispatchers.Main) {
                        // UI에 관한 코드 이므로 Main 에서 돌린다.
                        _location.add(location)
                    }
                }
                delay(interval)
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