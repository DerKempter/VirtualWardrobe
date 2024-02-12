package com.thekempter.virtualwardrobe.data

import com.thekempter.virtualwardrobe.data.room.BrandDao
import com.thekempter.virtualwardrobe.data.room.ClothingDao
import com.thekempter.virtualwardrobe.data.room.ImageDao
import com.thekempter.virtualwardrobe.data.room.OutfitDao
import com.thekempter.virtualwardrobe.data.room.SeasonDao
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import okhttp3.internal.wait

class WardrobeRepository(
    private val clothingDao: ClothingDao,
    private val outfitDao: OutfitDao,
    private val seasonDao: SeasonDao,
    private val imageDao: ImageDao,
    private val brandDao: BrandDao
    ){
    val allClothes = clothingDao.getAllClothes()
    val allTypes = clothingDao.getAllTypes()
    val allSeasons = seasonDao.getAllSeasons()
    val allOutfits = outfitDao.getAllOutfits()
    val allImages = imageDao.getAllImages()
    val allBrands = brandDao.getAllBrands()

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

    suspend fun addClothingImage(item: ClothingImage) {
        return withContext(Dispatchers.IO){
            imageDao.insert(item)
        }
    }

    suspend fun addBrand(brand: Brand): Long {
        return withContext(Dispatchers.IO){
            return@withContext brandDao.upsertBrand(brand)
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

    suspend fun getImageForClothingById(imageId: Int): List<ClothingImage> {
        return withContext(Dispatchers.IO) {
            imageDao.getImageForClothingById(imageId)
        }
    }

    suspend fun getLatestImage(): List<ClothingImage> {
        return withContext(Dispatchers.IO) {
            imageDao.getLatestImage()
        }
    }
}
