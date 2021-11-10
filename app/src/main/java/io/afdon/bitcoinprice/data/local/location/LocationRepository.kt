package io.afdon.bitcoinprice.data.local.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LocationRepository @Inject constructor(
    private val context: Context
) {

    private val client by lazy { LocationServices.getFusedLocationProviderClient(context) }

    private val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation() : Location = suspendCoroutine { continuation ->
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                client.removeLocationUpdates(this)
                result.lastLocation.run {
                    continuation.resume(this)
                }
            }
        }

        try {
            client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        } catch (e: SecurityException) {
            continuation.resumeWithException(e)
        }
    }
}