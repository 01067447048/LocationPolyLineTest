package com.jaehyeon.locationpolylinetest.di

import com.jaehyeon.locationpolylinetest.data.LocationRepositoryImpl
import com.jaehyeon.locationpolylinetest.domain.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

/**
 * Created by Jaehyeon on 2022/08/16.
 */
@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepository(tracker: LocationRepositoryImpl): LocationRepository

}