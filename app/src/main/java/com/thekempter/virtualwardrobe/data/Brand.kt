package com.thekempter.virtualwardrobe.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "brand")
data class Brand(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)
