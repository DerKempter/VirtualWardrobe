package com.thekempter.virtualwardrobe

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ClothingDao {
    @Insert
    fun insert(item: ClothingItem)

    @Update
    fun update(item: ClothingItem)

    @Delete
    fun delete(item: ClothingItem)

    @Query("SELECT * FROM clothing_items")
    fun getAllClothes(): List<ClothingItem>

    @Query("SELECT * FROM clothing_items WHERE type = :type")
    fun getItemsByType(type: String): List<ClothingItem>

    @Query("SELECT * FROM clothing_item_season WHERE clothingItemId = :clothingItemId")
    fun getSeasonsForClothingItem(clothingItemId: Int): List<ClothingItemSeasonCrossRef>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertClothingSeasonJunction(junction: ClothingItemSeasonCrossRef)

    @Delete
    fun deleteClothingSeasonJunction(junction: ClothingItemSeasonCrossRef)
}