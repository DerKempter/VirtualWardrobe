package com.thekempter.virtualwardrobe

import android.app.Application
import androidx.room.Room.databaseBuilder

class VirtualWardrobeApplication : Application() {

    private lateinit var database: WardrobeDatabase
    lateinit var repository: WardrobeRepository

    override fun onCreate() {
        super.onCreate()


        database = databaseBuilder(this, WardrobeDatabase::class.java, "clothing_db").build()
        repository = WardrobeRepository(database)
    }
}
