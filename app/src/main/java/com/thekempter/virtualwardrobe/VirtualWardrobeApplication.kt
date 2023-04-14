package com.thekempter.virtualwardrobe

import android.app.Application
import androidx.room.Room

class VirtualWardrobeApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(this, ClothingDatabase::class.java, "clothing_db").build()
    }
}
