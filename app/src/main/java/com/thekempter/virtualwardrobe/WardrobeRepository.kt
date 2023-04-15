package com.thekempter.virtualwardrobe

import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class WardrobeRepository(private val wardrobeDatabase: WardrobeDatabase) : WardrobeRepositoryInterface {
    private val clothingDao = wardrobeDatabase.clothingDao()
    private val outfitDao = wardrobeDatabase.outfitDao()

    override suspend fun addClothing(item: ClothingItem) {
        return withContext(Dispatchers.IO){
            clothingDao.insert(item)
        }
    }

    override suspend fun addOutfit(item: Outfit) {
        return withContext(Dispatchers.IO){
            outfitDao.insert(item)
        }
    }

    override suspend fun updateClothing(item: ClothingItem) {
        return withContext(Dispatchers.IO){
            clothingDao.update(item)
        }
    }

    override suspend fun updateOutfit(item: Outfit) {
        return withContext(Dispatchers.IO){
            outfitDao.update(item)
        }
    }

    override suspend fun deleteClothing(item: ClothingItem) {
        return withContext(Dispatchers.IO){
            clothingDao.delete(item)
        }
    }

    override suspend fun deleteClothing(item: Outfit) {
        return withContext(Dispatchers.IO){
            outfitDao.delete(item)
        }
    }

    override suspend fun getAllClothes(): List<ClothingItem> {
        return withContext(Dispatchers.IO){
            clothingDao.getAllClothes()
        }
    }

    override suspend fun getAllOutfits(): List<Outfit> {
        return withContext(Dispatchers.IO){
            outfitDao.getAllOutfits()
        }
    }
}
