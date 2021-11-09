package io.afdon.bitcoinprice.domain.repository

import io.afdon.bitcoinprice.domain.entity.BitcoinDataEntity
import kotlinx.coroutines.flow.Flow

interface BitcoinRepository {

    fun getBitcoinData() : Flow<List<BitcoinDataEntity>>
}