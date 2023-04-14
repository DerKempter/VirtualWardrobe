package com.thekempter.virtualwardrobe

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ClothingItem::class], version = 1, exportSchema = true)
abstract class ClothingDatabase : RoomDatabase() {
    abstract fun clothingDao(): ClothingDao
}
