package com.thekempter.virtualwardrobe

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity(tableName = "clothing_items")
data class ClothingItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String,
    val color: String,
    val brand: String,
    val size: String,
    val material: String,
    val imageUrl: String
)

@Dao
interface ClothingDao {
    @Insert
    suspend fun insert(item: ClothingItem)

    @Update
    suspend fun update(item: ClothingItem)

    @Delete
    suspend fun delete(item: ClothingItem)

    @Query("SELECT * FROM clothing_items")
    suspend fun getAllItems(): List<ClothingItem>

    @Query("SELECT * FROM clothing_items WHERE type = :type")
    suspend fun getItemsByType(type: String): List<ClothingItem>
}

