package com.thekempter.virtualwardrobe

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "clothing_item_season",
    primaryKeys = ["clothingItemId", "seasonId"],
    foreignKeys = [
        ForeignKey(entity = ClothingItem::class, parentColumns = ["id"], childColumns = ["clothingItemId"]),
        ForeignKey(entity = Season::class, parentColumns = ["id"], childColumns = ["seasonId"])
    ],
    indices = [
        Index(value = ["clothingItemId"]),
        Index(value = ["seasonId"])
    ]
)
data class ClothingItemSeasonCrossRef(
    val clothingItemId: Int,
    val seasonId: Int
)
