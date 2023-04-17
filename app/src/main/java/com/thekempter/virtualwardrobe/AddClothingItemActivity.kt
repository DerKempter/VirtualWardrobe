package com.thekempter.virtualwardrobe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import com.thekempter.virtualwardrobe.data.ClothingItem
import com.thekempter.virtualwardrobe.data.ClothingType
import com.thekempter.virtualwardrobe.ui.components.dialog.AddClothingItemScreen

class AddClothingItemActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddClothingItemScreen(
                ::onSaveButtonClicked,
                ::addDefaultClothingTypes,
                name,
                type,
                color,
                brand,
                size,
                material,
                imageUrl
            )
        }
    }

    var name = mutableStateOf("")
    var type = mutableStateOf(ClothingType(-1, "dummy"))
    var color = mutableStateOf("")
    var brand = mutableStateOf("")
    var size = mutableStateOf("")
    var material = mutableStateOf("")
    var imageUrl = mutableStateOf("")



    private fun addDefaultClothingTypes(viewModel: ClothingViewModel){
        val clothingTypeNames = arrayOf(
            "Pullover", "Skirt", "Dress", "Pants", "Shorts", "Jumpsuit",
            "Jacket", "Shoes", "Jewelery", "Belt", "Shirt", "Hoodie", "Crop-Top"
        )
        clothingTypeNames.forEach { clothingTypeName ->
            viewModel.addClothingType(ClothingType(name = clothingTypeName))
        }
    }

    fun onSaveButtonClicked(clothingViewModel: ClothingViewModel) {
        val clothingType = type.value
        val clothingItem = ClothingItem(
            name = name.value,
            material = material.value,
            color = color.value,
            brand = brand.value,
            size = size.value,
            imageUrl = imageUrl.value,
            typeId = clothingType.id
        )
        clothingViewModel.addClothingItem(clothingItem)
        finish()
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
    }
}


