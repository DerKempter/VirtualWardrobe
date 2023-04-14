package com.thekempter.virtualwardrobe

import android.app.Application
import androidx.room.Room

class VirtualWardrobeApplication : Application() {
    companion object{
        lateinit var database: ClothingDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, ClothingDatabase::class.java, "clothing_db").build()
    }
}
