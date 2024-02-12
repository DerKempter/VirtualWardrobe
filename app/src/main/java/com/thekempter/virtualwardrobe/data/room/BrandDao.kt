package com.thekempter.virtualwardrobe.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.thekempter.virtualwardrobe.data.Brand
import com.thekempter.virtualwardrobe.data.ClothingType
import kotlinx.coroutines.flow.Flow

@Dao
interface BrandDao {
    @Upsert
    fun upsertBrand(brand: Brand): Long

    @Delete
    fun deleteBrand(brand: Brand)

    @Query("SELECT * FROM brand")
    fun getAllBrands(): Flow<List<Brand>>
}