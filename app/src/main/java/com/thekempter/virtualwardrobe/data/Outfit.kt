package com.thekempter.virtualwardrobe.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "outfits")
data class Outfit(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "date_created")
    val dateCreated: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "clothing_items")
    val clothingItems: String, // comma-separated list of clothing item IDs

    @ColumnInfo(name = "occasion")
    val occasion: String?
)

