package io.afdon.bitcoinprice.domain.repository

import io.afdon.bitcoinprice.domain.entity.BitcoinDataEntity
import io.afdon.bitcoinprice.domain.entity.RequestState
import kotlinx.coroutines.flow.Flow

interface BitcoinRepository {

    fun getAll() : Flow<List<BitcoinDataEntity>>

    fun requestNewBitcoinData() : Flow<RequestState<Unit>>

    suspend fun removePreviousData()
}