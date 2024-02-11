package com.thekempter.virtualwardrobe.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "clothing_items",
    foreignKeys = [
        ForeignKey(
            entity = ClothingType::class,
            parentColumns = ["id"],
            childColumns = ["typeId"],
            onUpdate = CASCADE
        ),
        ForeignKey(
            entity = ClothingImage::class,
            parentColumns = ["id"],
            childColumns = ["imageId"],
            onUpdate = CASCADE
        ),
        ForeignKey(
            entity = Brand::class,
            parentColumns = ["id"],
            childColumns = ["brandId"],
            onUpdate = CASCADE
        )
    ],
    indices = [
        Index("id"), Index("typeId"), Index("imageId")
    ]
)
data class ClothingItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val typeId: Int, // foreign key to clothing_type table
    val color: String,
    val brandId: Int, // foreign key to brand table
    val size: String,
    val material: String,
    val imageId: Int // foreign key to column clothing_image
)
