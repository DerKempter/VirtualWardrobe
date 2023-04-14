package com.thekempter.virtualwardrobe

interface ClothingRepositoryInterface {
    suspend fun addItem(item: ClothingItem)
    suspend fun updateItem(item: ClothingItem)
    suspend fun deleteItem(item: ClothingItem)
    suspend fun getAllItems(): List<ClothingItem>
}