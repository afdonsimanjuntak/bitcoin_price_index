package io.afdon.bitcoinprice.data.remote.repository

import android.location.Location
import android.util.Log
import io.afdon.bitcoinprice.data.local.location.LocationRepository
import io.afdon.bitcoinprice.data.local.room.BpiDao
import io.afdon.bitcoinprice.data.local.room.DbBpiEntity
import io.afdon.bitcoinprice.data.remote.CoindeskApi
import io.afdon.bitcoinprice.data.remote.dto.BitcoinPriceIndexDto
import io.afdon.bitcoinprice.domain.entity.BitcoinDataEntity
import io.afdon.bitcoinprice.domain.entity.RequestState
import io.afdon.bitcoinprice.domain.repository.BitcoinRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class BitcoinDataRepositoryImpl @Inject constructor(
    private val coindeskApi: CoindeskApi,
    private val locationRepository: LocationRepository,
    private val bpiDao: BpiDao
) : BitcoinRepository {

    private val apiTimeFormat = SimpleDateFormat("MMM d, yyyy HH:mm:ss z", Locale.getDefault())
    private val localTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun getAll(): Flow<List<BitcoinDataEntity>> = bpiDao.getAll().map { list ->
        list.map {
            BitcoinDataEntity(it.time, it.timeFloat, it.rate, it.rateFloat, it.latitude, it.longitude)
        }
    }

    override fun requestNewBitcoinData(): Flow<RequestState<Unit>> = flow {
        emit(RequestState.Loading(true))
        coroutineScope {
            val apiDeferred = async {
                coindeskApi.getBitcoinData()
            }
            val locationDeferred = async {
                locationRepository.getCurrentLocation()
            }
            val bitcoinPriceIndexDto = apiDeferred.await().body()
            val location = locationDeferred.await()
            Log.d("afdon", "getBitcoinData: lat: ${location.latitude} long: ${location.longitude}")
            saveToDb(bitcoinPriceIndexDto, location)
        }
        emit(RequestState.Success(Unit))
        emit(RequestState.Loading(false))
    }

    private suspend fun saveToDb(bpiDto: BitcoinPriceIndexDto?, location: Location) {
        val date = bpiDto?.time?.updated?.let { apiTimeFormat.parse(it) }
        val bpi = DbBpiEntity(
            getCurrentTime(),
            date?.let { localTimeFormat.format(it) } ?: "",
            date?.let { calculateTimeDecimal(it) } ?: 0f,
            bpiDto?.bpi?.get("USD")?.rate as String,
            bpiDto.bpi["USD"]?.rateFloat as Float,
            location.latitude.toString(),
            location.longitude.toString()
        )
        bpiDao.add(bpi)
    }

    private fun calculateTimeDecimal(date: Date) : Float {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        return (hour.toFloat() + (minute.toFloat() / 60f))
    }

    private fun getCurrentTime() : String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())
        return sdf.format(calendar.time)
    }
}