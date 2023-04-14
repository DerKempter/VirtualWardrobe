package com.thekempter.virtualwardrobe

class ClothingRepository(private val clothingDao: ClothingDao) : ClothingRepositoryInterface {
    override suspend fun addItem(item: ClothingItem) {
        clothingDao.insert(item)
    }

    override suspend fun updateItem(item: ClothingItem) {
        clothingDao.update(item)
    }

    override suspend fun deleteItem(item: ClothingItem) {
        clothingDao.delete(item)
    }

    override suspend fun getAllItems(): List<ClothingItem> {
        return clothingDao.getAllClothes()
    }
}
