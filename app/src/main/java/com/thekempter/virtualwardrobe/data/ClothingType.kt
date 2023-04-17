package com.thekempter.virtualwardrobe.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clothing_types")
data class ClothingType(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)