package com.thekempter.virtualwardrobe

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ClothingItem::class, Outfit::class], version = 1, exportSchema = true)
abstract class WardrobeDatabase : RoomDatabase() {
    abstract fun clothingDao(): ClothingDao
    abstract fun outfitDao(): OutfitDao
}
