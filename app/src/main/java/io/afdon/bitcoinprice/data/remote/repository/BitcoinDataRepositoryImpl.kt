package io.afdon.bitcoinprice.data.remote.repository

import io.afdon.bitcoinprice.data.remote.CoindeskApi
import io.afdon.bitcoinprice.domain.entity.BitcoinDataEntity
import io.afdon.bitcoinprice.domain.repository.BitcoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BitcoinDataRepositoryImpl @Inject constructor(
    private val coindeskApi: CoindeskApi
) : BitcoinRepository {

    override fun getBitcoinData(): Flow<List<BitcoinDataEntity>> = flow {
        val dummyList = arrayListOf<BitcoinDataEntity>().apply {
            add(BitcoinDataEntity("00:03", 0.1f, "USD 65,000", 65000.0f, "234.12343", "234.3234"))
            add(BitcoinDataEntity("01:00", 1.0f, "USD 65,000", 61000.0f, "234.12343", "234.3234"))
            add(BitcoinDataEntity("03:00", 3.0f, "USD 65,000", 66000.0f, "234.12343", "234.3234"))
            add(BitcoinDataEntity("04:03", 4.1f, "USD 65,000", 69000.0f, "234.12343", "234.3234"))
            add(BitcoinDataEntity("07:00", 7.0f, "USD 65,000", 64000.0f, "234.12343", "234.3234"))
            add(BitcoinDataEntity("08:29", 8.5f, "USD 65,000", 55000.0f, "234.12343", "234.3234"))
            add(BitcoinDataEntity("09:15", 9.2f, "USD 65,000", 52000.0f, "234.12343", "234.3234"))
            add(BitcoinDataEntity("12:25", 12.4f, "USD 65,000", 55000.0f, "234.12343", "234.3234"))
            add(BitcoinDataEntity("15:03", 15.1f, "USD 65,000", 55000.0f, "234.12343", "234.3234"))
        }
        emit(dummyList)
    }
}