package com.thekempter.virtualwardrobe

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ClothingItem::class], version = 1)
abstract class ClothingDatabase : RoomDatabase() {
    abstract fun clothingDao(): ClothingDao
}
