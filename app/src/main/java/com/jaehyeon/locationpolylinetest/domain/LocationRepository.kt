package com.jaehyeon.locationpolylinetest.domain

import android.location.Location

/**
 * Created by Jaehyeon on 2022/08/16.
 */
interface LocationRepository {
    suspend fun getCurrentLocation(): Location?
}