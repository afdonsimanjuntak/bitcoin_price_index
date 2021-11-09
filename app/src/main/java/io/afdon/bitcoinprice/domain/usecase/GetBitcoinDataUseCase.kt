package io.afdon.bitcoinprice.domain.usecase

import io.afdon.bitcoinprice.domain.entity.BitcoinDataEntity
import io.afdon.bitcoinprice.domain.repository.BitcoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBitcoinDataUseCase @Inject constructor(
    private val bitcoinRepository: BitcoinRepository
) {

    fun getBitcoinData() : Flow<List<BitcoinDataEntity>> = bitcoinRepository.getBitcoinData()
}