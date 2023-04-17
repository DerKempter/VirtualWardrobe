package com.thekempter.virtualwardrobe

import kotlinx.coroutines.flow.Flow

interface WardrobeRepositoryInterface {
    suspend fun addClothing(item: ClothingItem)
    suspend fun addOutfit(item: Outfit)
    suspend fun addClothingType(item: ClothingType)
    suspend fun updateClothing(item: ClothingItem)
    suspend fun updateOutfit(item: Outfit)
    suspend fun deleteClothing(item: ClothingItem)
    suspend fun deleteClothing(item: Outfit)
    suspend fun getAllClothes(): List<ClothingItem>
    suspend fun getAllOutfits(): List<Outfit>
    suspend fun getAllSeasons(): List<Season>
    fun getAllClothingTypes(): Flow<List<ClothingType>>
    suspend fun getSeasonsByClothingId(item: ClothingItem): List<Season>
    suspend fun getClothingTypeByClothing(item: ClothingItem): ClothingType
}