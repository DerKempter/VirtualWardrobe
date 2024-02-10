package com.thekempter.virtualwardrobe.ui.components.collection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.thekempter.virtualwardrobe.ClothingViewModel
import com.thekempter.virtualwardrobe.data.util.getClothingTypeForClothing
import com.thekempter.virtualwardrobe.data.util.getSeasonsForClothing
import com.thekempter.virtualwardrobe.ui.components.ClothingItemDisplay

@Composable
fun CollectionScreen(clothingViewModel: ClothingViewModel, navController: NavController){
    val state by clothingViewModel.state.collectAsState()
    val allClothes = state.clothes

    LazyColumn {
        items(items = allClothes, key = {it.id}) { clothingItem ->
            val seasons = getSeasonsForClothing(clothingViewModel, clothingItem.id)
            val clothingType = getClothingTypeForClothing(state, clothingItem.typeId)

            ClothingItemDisplay(clothingItem, seasons, clothingType, clothingViewModel, navController)
        }
    }
}
