package com.thekempter.virtualwardrobe.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.thekempter.virtualwardrobe.data.ClothingItem
import com.thekempter.virtualwardrobe.data.ClothingItemSeasonCrossRef
import com.thekempter.virtualwardrobe.data.ClothingType
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothingDao {
    @Insert
    fun insert(item: ClothingItem)

    @Update
    fun update(item: ClothingItem)

    @Delete
    fun delete(item: ClothingItem)

    @Query("SELECT * FROM clothing_items")
    fun getAllClothes(): Flow<List<ClothingItem>>

    @Query("SELECT * FROM clothing_types")
    fun getAllTypes(): Flow<List<ClothingType>>

    @Query("SELECT * FROM clothing_items WHERE typeId = :type")
    fun getItemsByType(type: String): List<ClothingItem>

    @Query("SELECT * FROM clothing_item_season WHERE clothingItemId = :clothingItemId")
    fun getSeasonsForClothingItem(clothingItemId: Int): List<ClothingItemSeasonCrossRef>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertClothingSeasonJunction(junction: ClothingItemSeasonCrossRef)

    @Delete
    fun deleteClothingSeasonJunction(junction: ClothingItemSeasonCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(type: ClothingType)

    @Query("SELECT * FROM clothing_types WHERE Id = :clothingTypeId")
    fun getTypeForClothing(clothingTypeId: Int): ClothingType
}