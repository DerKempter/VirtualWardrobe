package com.thekempter.virtualwardrobe

interface WardrobeRepositoryInterface {
    suspend fun addClothing(item: ClothingItem)
    suspend fun addOutfit(item: Outfit)
    suspend fun updateClothing(item: ClothingItem)
    suspend fun updateOutfit(item: Outfit)
    suspend fun deleteClothing(item: ClothingItem)
    suspend fun deleteClothing(item: Outfit)
    suspend fun getAllClothes(): List<ClothingItem>
    suspend fun getAllOutfits(): List<Outfit>
    suspend fun getAllSeasons(): List<Season>
    suspend fun getAllClothingTypes(): List<ClothingType>
    suspend fun getSeasonsByClothingId(item: ClothingItem): List<Season>
    suspend fun getClothingTypeByClothing(item: ClothingItem): ClothingType
}