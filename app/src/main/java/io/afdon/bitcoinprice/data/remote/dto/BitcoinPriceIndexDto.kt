package io.afdon.bitcoinprice.data.remote.dto


import com.google.gson.annotations.SerializedName

data class BitcoinPriceIndexDto(
    @SerializedName("time")
    val time: Time?,
    @SerializedName("disclaimer")
    val disclaimer: String?,
    @SerializedName("chartName")
    val chartName: String?,
    @SerializedName("bpi")
    val bpi: Map<String, Bpi>?
) {
    data class Time(
        @SerializedName("updated")
        val updated: String?,
        @SerializedName("updatedISO")
        val updatedISO: String?,
        @SerializedName("updateduk")
        val updateduk: String?
    )

    data class Bpi(
        @SerializedName("code")
        val code: String?,
        @SerializedName("symbol")
        val symbol: String?,
        @SerializedName("rate")
        val rate: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("rate_float")
        val rateFloat: Float?
    )
}