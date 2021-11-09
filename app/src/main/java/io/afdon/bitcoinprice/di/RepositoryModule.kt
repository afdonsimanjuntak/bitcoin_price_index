package io.afdon.bitcoinprice.di

import dagger.Binds
import dagger.Module
import io.afdon.bitcoinprice.data.remote.repository.BitcoinDataRepositoryImpl
import io.afdon.bitcoinprice.domain.repository.BitcoinRepository
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindBitcoinDataRepository(repository: BitcoinDataRepositoryImpl) : BitcoinRepository
}