package com.thekempter.virtualwardrobe.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thekempter.virtualwardrobe.data.ClothingItem
import com.thekempter.virtualwardrobe.data.ClothingItemSeasonCrossRef
import com.thekempter.virtualwardrobe.data.ClothingType
import com.thekempter.virtualwardrobe.data.ClothingImage
import com.thekempter.virtualwardrobe.data.Outfit
import com.thekempter.virtualwardrobe.data.Season

@Database(
    entities = [
        ClothingItem::class,
        ClothingType::class,
        Outfit::class,
        Season::class,
        ClothingItemSeasonCrossRef::class,
        ClothingImage::class
               ],
    version = 1,
    exportSchema = true
)
abstract class WardrobeDatabase : RoomDatabase() {
    abstract fun clothingDao(): ClothingDao
    abstract fun outfitDao(): OutfitDao
    abstract fun seasonDao(): SeasonDao
    abstract fun imageDao(): ImageDao

    companion object {
        @Volatile
        private var INSTANCE: WardrobeDatabase? = null
        fun getDatabase(context: Context): WardrobeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WardrobeDatabase::class.java,
                    "wardrobe_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

