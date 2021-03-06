package io.afdon.bitcoinprice.data.remote

import io.afdon.bitcoinprice.data.remote.dto.BitcoinPriceIndexDto
import retrofit2.Response
import retrofit2.http.GET

interface CoindeskApi {

    @GET("/v1/bpi/currentprice.json")
    suspend fun getBitcoinData() : Response<BitcoinPriceIndexDto>
}