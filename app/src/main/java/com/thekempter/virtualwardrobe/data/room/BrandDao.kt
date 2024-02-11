package com.thekempter.virtualwardrobe.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert
import com.thekempter.virtualwardrobe.data.Brand

@Dao
interface BrandDao {
    @Upsert
    fun upsertBrand(brand: Brand)

    @Delete
    fun deleteBrand(brand: Brand)
}