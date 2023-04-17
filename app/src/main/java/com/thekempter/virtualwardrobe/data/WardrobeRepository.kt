package com.thekempter.virtualwardrobe.data

import com.thekempter.virtualwardrobe.data.room.ClothingDao
import com.thekempter.virtualwardrobe.data.room.OutfitDao
import com.thekempter.virtualwardrobe.data.room.SeasonDao
import com.thekempter.virtualwardrobe.data.room.WardrobeDatabase
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class WardrobeRepository(private val clothingDao: ClothingDao, private val outfitDao: OutfitDao, private val seasonDao: SeasonDao){
    val allClothes = clothingDao.getAllClothes()
    val allTypes = clothingDao.getAllTypes()
    val allSeasons = seasonDao.getAllSeasons()
    val allOutfits = outfitDao.getAllOutfits()

    suspend fun addClothing(item: ClothingItem) {
        return withContext(Dispatchers.IO){
            clothingDao.insert(item)
        }
    }

    suspend fun addOutfit(item: Outfit) {
        return withContext(Dispatchers.IO){
            outfitDao.insert(item)
        }
    }

    suspend fun addClothingType(item: ClothingType) {
        return withContext(Dispatchers.IO){
            clothingDao.insert(item)
        }
    }

    suspend fun updateClothing(item: ClothingItem) {
        return withContext(Dispatchers.IO){
            clothingDao.update(item)
        }
    }

    suspend fun updateOutfit(item: Outfit) {
        return withContext(Dispatchers.IO){
            outfitDao.update(item)
        }
    }

    suspend fun deleteClothing(item: ClothingItem) {
        return withContext(Dispatchers.IO){
            clothingDao.delete(item)
        }
    }

    suspend fun deleteClothing(item: Outfit) {
        return withContext(Dispatchers.IO){
            outfitDao.delete(item)
        }
    }

    suspend fun getSeasonsByClothingId(clothingId: Int): List<Season> {
        return withContext(Dispatchers.IO) {
            seasonDao.getSeasonsForClothingItem(clothingId)
        }
    }

    suspend fun getClothingTypeByClothing(typeId: Int): ClothingType {
        return withContext(Dispatchers.IO) {
            clothingDao.getTypeForClothing(typeId)
        }
    }
}
