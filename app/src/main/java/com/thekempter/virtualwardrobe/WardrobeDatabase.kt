package com.thekempter.virtualwardrobe

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ClothingItem::class,
        ClothingType::class,
        Outfit::class,
        Season::class,
        ClothingItemSeasonCrossRef::class
               ],
    version = 1,
    exportSchema = true
)
abstract class WardrobeDatabase : RoomDatabase() {
    abstract fun clothingDao(): ClothingDao
    abstract fun outfitDao(): OutfitDao
    abstract fun seasonDao(): SeasonDao
}

