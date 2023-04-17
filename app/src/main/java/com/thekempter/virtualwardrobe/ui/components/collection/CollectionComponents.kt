package com.thekempter.virtualwardrobe.ui.components.collection

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.thekempter.virtualwardrobe.ClothingViewModel
import com.thekempter.virtualwardrobe.data.util.getClothingTypeForClothing
import com.thekempter.virtualwardrobe.data.util.getSeasonsForClothing
import com.thekempter.virtualwardrobe.ui.components.ClothingItemDisplay

@Composable
fun CollectionScreen(clothingViewModel: ClothingViewModel){
    val state by clothingViewModel.state.collectAsState()
    val allClothes = state.clothes

    Column {
        allClothes.forEach { clothingItem ->
            val seasons = getSeasonsForClothing(clothingViewModel, clothingItem.id)
            val clothingType = getClothingTypeForClothing(state, clothingItem.typeId)

            ClothingItemDisplay(clothingItem, seasons, clothingType)
        }
    }
}
