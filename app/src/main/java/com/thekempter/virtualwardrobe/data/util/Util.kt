package com.thekempter.virtualwardrobe.data.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.thekempter.virtualwardrobe.ClothingViewModel
import com.thekempter.virtualwardrobe.ClothingViewState
import com.thekempter.virtualwardrobe.data.ClothingType
import com.thekempter.virtualwardrobe.data.Season

fun getSeasonsForClothing(clothingViewModel: ClothingViewModel, clothingId: Int): List<Season> {
    val seasons by mutableStateOf<List<Season>>(emptyList())
    clothingViewModel.getSeasonsForClothingId(clothingId) {seasonList ->
        seasons.union(seasonList)
    }
    return seasons
}

fun getClothingTypeForClothing(state: ClothingViewState, typeId: Int): ClothingType {
    return state.clothingTypes.find { type -> type.id == typeId } ?: ClothingType(
        -1,
        "missing Type"
    )
}
