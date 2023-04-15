package com.thekempter.virtualwardrobe

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface OutfitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(outfit: Outfit)

    @Update
    suspend fun update(outfit: Outfit)

    @Delete
    suspend fun delete(outfit: Outfit)

    @Query("SELECT * FROM outfits")
    fun getAllOutfits(): List<Outfit>

    // Add any other query methods you need here
}
