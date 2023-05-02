package com.thekempter.virtualwardrobe

import android.app.Application

class VirtualWardrobeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}
