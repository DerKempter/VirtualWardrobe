package com.thekempter.virtualwardrobe

import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class ClothingRepository(private val clothingDatabase: ClothingDatabase) : ClothingRepositoryInterface {
    private val clothingDao = clothingDatabase.clothingDao()

    override suspend fun addItem(item: ClothingItem) {
        return withContext(Dispatchers.IO){
            clothingDao.insert(item)
        }
    }

    override suspend fun updateItem(item: ClothingItem) {
        return withContext(Dispatchers.IO){
            clothingDao.update(item)
        }
    }

    override suspend fun deleteItem(item: ClothingItem) {
        return withContext(Dispatchers.IO){
            clothingDao.delete(item)
        }
    }

    override suspend fun getAllItems(): List<ClothingItem> {
        return withContext(Dispatchers.IO){
            clothingDao.getAllClothes()
        }
    }
}
