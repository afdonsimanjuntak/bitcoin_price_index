package io.afdon.bitcoinprice.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BpiDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(bpi: DbBpiEntity)

    @Query("SELECT * FROM bpi")
    fun getAll(): Flow<List<DbBpiEntity>>
}