package com.thekempter.virtualwardrobe.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seasons")
data class Season(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val dateCreated: Long = System.currentTimeMillis()
)

