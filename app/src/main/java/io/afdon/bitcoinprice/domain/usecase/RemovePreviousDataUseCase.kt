package io.afdon.bitcoinprice.domain.usecase

import io.afdon.bitcoinprice.domain.repository.BitcoinRepository
import javax.inject.Inject

class RemovePreviousDataUseCase @Inject constructor(
    private val bitcoinRepository: BitcoinRepository
) {

    suspend operator fun invoke() = bitcoinRepository.removePreviousData()
}