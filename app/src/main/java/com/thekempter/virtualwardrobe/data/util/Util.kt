package com.thekempter.virtualwardrobe.data.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.thekempter.virtualwardrobe.ClothingViewModel
import com.thekempter.virtualwardrobe.ClothingViewState
import com.thekempter.virtualwardrobe.data.Brand
import com.thekempter.virtualwardrobe.data.ClothingImage
import com.thekempter.virtualwardrobe.data.ClothingType
import com.thekempter.virtualwardrobe.data.Season

fun getSeasonsForClothing(clothingViewModel: ClothingViewModel, clothingId: Int): List<Season> {
    val seasons by mutableStateOf<List<Season>>(emptyList())
    clothingViewModel.getSeasonsForClothingId(clothingId) {seasonList ->
        seasons.union(seasonList)
    }
    return seasons
}

@Composable
fun getImageForClothingById(clothingViewModel: ClothingViewModel, clothingId: Int): State<List<ClothingImage>> {
    return remember { mutableStateOf(emptyList<ClothingImage>()) }.apply {
        LaunchedEffect(clothingId){
            val imageList = clothingViewModel.getImageForClothingById(clothingId)
            value = imageList
        }
    }
}

fun getLatestImage(clothingViewModel: ClothingViewModel): List<ClothingImage> {
    val clothingImage by mutableStateOf<List<ClothingImage>>(emptyList())
    clothingViewModel.getLatestImage() {image ->
        clothingImage.union(image)
    }
    return clothingImage
}

fun getClothingTypeForClothing(state: ClothingViewState, typeId: Int): ClothingType {
    return state.clothingTypes.find { type -> type.id == typeId } ?: ClothingType(
        -1,
        "missing Type"
    )
}

fun getBrandForClothing(state: ClothingViewState, brandId: Int): Brand {
    return state.brands.find { brand -> brand.id == brandId } ?: Brand(
        -1,
        "missing Type"
    )
}
