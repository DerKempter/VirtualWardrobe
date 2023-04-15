package com.thekempter.virtualwardrobe

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "clothing_items",
    foreignKeys = [
        ForeignKey(
            entity = ClothingType::class,
            parentColumns = ["id"],
            childColumns = ["typeId"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class ClothingItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val typeId: Int, // foreign key to clothing_type table
    val color: String,
    val brand: String,
    val size: String,
    val material: String,
    val imageUrl: String
)
