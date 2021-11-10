package io.afdon.bitcoinprice.data.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bpi")
data class DbBpiEntity(
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "time_float") val timeFloat: Float,
    @ColumnInfo(name = "rate") val rate: String,
    @ColumnInfo(name = "rate_float") val rateFloat: Float,
    @ColumnInfo(name = "latitude") val latitude: String,
    @ColumnInfo(name = "longitude") val longitude: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}