package com.thekempter.virtualwardrobe

import android.content.Context
import com.thekempter.virtualwardrobe.data.WardrobeRepository
import com.thekempter.virtualwardrobe.data.room.WardrobeDatabase

object Graph {
    lateinit var database: WardrobeDatabase
        private set
    val wardrobeRepo by lazy {
        WardrobeRepository(
            database.clothingDao(),
            database.outfitDao(),
            database.seasonDao(),
            database.imageDao()
        )
    }

    fun provide(context: Context){
        database = WardrobeDatabase.getDatabase(context)
    }
}