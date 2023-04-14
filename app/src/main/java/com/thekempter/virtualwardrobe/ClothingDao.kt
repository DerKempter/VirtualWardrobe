package com.thekempter.virtualwardrobe

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ClothingDao {
    @Insert
    suspend fun insert(item: ClothingItem)

    @Update
    suspend fun update(item: ClothingItem)

    @Delete
    suspend fun delete(item: ClothingItem)

    @Query("SELECT * FROM clothing_items")
    suspend fun getAllClothes(): List<ClothingItem>

    @Query("SELECT * FROM clothing_items WHERE type = :type")
    suspend fun getItemsByType(type: String): List<ClothingItem>
}