package com.thekempter.virtualwardrobe

import android.app.Application
import androidx.room.Room.databaseBuilder

class VirtualWardrobeApplication : Application() {

    private lateinit var database: ClothingDatabase
    lateinit var repository: ClothingRepository

    override fun onCreate() {
        super.onCreate()


        database = databaseBuilder(this, ClothingDatabase::class.java, "clothing_db").build()
        repository = ClothingRepository(database)
    }
}
