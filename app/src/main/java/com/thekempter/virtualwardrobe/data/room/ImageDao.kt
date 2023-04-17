package com.thekempter.virtualwardrobe.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.thekempter.virtualwardrobe.data.ClothingImage
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Insert
    fun insert(item: ClothingImage)

    @Update
    fun update(item: ClothingImage)

    @Delete
    fun delete(item: ClothingImage)

    @Query("SELECT * FROM images")
    fun getAllImages(): Flow<List<ClothingImage>>

    @Query("SELECT * FROM images WHERE id = :imageId")
    fun getImageForClothingById(imageId: Int): List<ClothingImage>

    @Query("SELECT * FROM images ORDER BY id DESC LIMIT 1")
    fun getLatestImage(): List<ClothingImage>
}