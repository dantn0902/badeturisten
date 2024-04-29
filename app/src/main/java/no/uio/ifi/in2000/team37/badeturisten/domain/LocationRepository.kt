package no.uio.ifi.in2000.team37.badeturisten.domain

import android.content.Context
import android.location.Location
import kotlinx.coroutines.flow.StateFlow

interface LocationRepository {
    val locationData: StateFlow<Location?>

    fun fetchLastLocation()

    fun fetchCurrentLocation()
}
