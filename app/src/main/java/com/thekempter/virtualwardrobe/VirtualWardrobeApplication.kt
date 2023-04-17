package com.thekempter.virtualwardrobe

import android.app.Application
import androidx.room.Room.databaseBuilder
import com.thekempter.virtualwardrobe.data.room.WardrobeDatabase

class VirtualWardrobeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}
