package io.afdon.bitcoinprice.domain.entity

data class BitcoinDataEntity(
    val time: String,
    val timeFloat: Float,
    val rate: String,
    val rateFloat: Float,
    val latitude: String,
    val longitude: String
)