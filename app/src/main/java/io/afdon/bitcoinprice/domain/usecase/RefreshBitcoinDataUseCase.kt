package io.afdon.bitcoinprice.domain.usecase

import io.afdon.bitcoinprice.domain.entity.RequestState
import io.afdon.bitcoinprice.domain.repository.BitcoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RefreshBitcoinDataUseCase @Inject constructor(
    private val bitcoinRepository: BitcoinRepository
) {

    fun requestNewBitcoinData() : Flow<RequestState<Unit>> = bitcoinRepository.requestNewBitcoinData()
}