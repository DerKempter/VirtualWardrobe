package com.thekempter.virtualwardrobe

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import com.thekempter.virtualwardrobe.data.Brand
import com.thekempter.virtualwardrobe.data.ClothingImage
import com.thekempter.virtualwardrobe.data.ClothingImageTypeConverter
import com.thekempter.virtualwardrobe.data.ClothingItem
import com.thekempter.virtualwardrobe.data.ClothingType
import com.thekempter.virtualwardrobe.ui.components.dialog.AddClothingItemScreen
import kotlinx.coroutines.launch

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
                imageUrl,
                imageBitmap,
                requestPermissionLauncher,
                permissionGranted
            )
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted){
                Log.d("AddClothingItemActivity", "Camera Permission granted")
            } else {
                Log.d("AddClothingItemActivity", "Camera Permission denied")
            }
            permissionGranted.value = isGranted
        }

    var name = mutableStateOf("")
    var type = mutableStateOf(ClothingType(-1, "dummy"))
    var color = mutableStateOf("")
    var brand = mutableStateOf(Brand(id = -1, name = ""))
    var size = mutableStateOf("")
    var material = mutableStateOf("")
    var imageUrl = mutableStateOf(Uri.parse(""))
    var imageBitmap = mutableStateOf(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888))
    private var permissionGranted = mutableStateOf(false)

    private fun addDefaultClothingTypes(viewModel: ClothingViewModel){
        val clothingTypeNames = arrayOf(
            "Pullover", "Skirt", "Dress", "Pants", "Shorts", "Jumpsuit",
            "Jacket", "Shoes", "Jewelery", "Belt", "Shirt", "Hoodie", "Crop-Top"
        )
        clothingTypeNames.forEach { clothingTypeName ->
            viewModel.addClothingType(ClothingType(name = clothingTypeName))
        }
    }

    private fun onSaveButtonClicked(clothingViewModel: ClothingViewModel) {
        clothingViewModel.viewModelScope.launch {
            val clothingImageList = saveImageToDatabase(clothingViewModel)
            val clothingImage = clothingImageList.first()

            val brandId = saveBrandToDatabase(clothingViewModel)
            Log.d("onSaveButtonClicked", "Brand Id stored in DB: $brandId")

            val clothingType = type.value
            val clothingItem = ClothingItem(
                name = name.value,
                material = material.value,
                color = color.value,
                brandId = brandId.toInt(),
                size = size.value,
                imageId = clothingImage.id,
                typeId = clothingType.id
            )
            clothingViewModel.addClothingItem(clothingItem)
            finish()
        }
    }

    private suspend fun saveImageToDatabase(clothingViewModel: ClothingViewModel): List<ClothingImage> {
        val bitmap = imageBitmap.value
        val maxSizeBytes = 5000000
        val sizeBytes = bitmap.byteCount
        var compressionRatio = 1F
        if (sizeBytes > maxSizeBytes){
            Log.d(
                "AddClothingItemActivity",
                "Bytecount before compression: $sizeBytes bytes"
            )
            compressionRatio = maxSizeBytes.toFloat() / sizeBytes.toFloat()
        }
        val byteArray = ClothingImageTypeConverter().toByteArray(bitmap, compressionRatio)
        val clothingImageToSave = ClothingImage(bitData = byteArray)
        clothingViewModel.addClothingImage(clothingImageToSave)
        val clothingImage = clothingViewModel.getLatestImage()
        Log.d(
            "AddClothingItemActivity",
            "saved bitmap to database: ${byteArray.size} bytes"
        )
        return clothingImage
    }

    private suspend fun saveBrandToDatabase(clothingViewModel: ClothingViewModel): Long {
        val brandToSave = Brand(name = brand.value.name)
        val brandId = clothingViewModel.addBrand(brandToSave)
        Log.d("saveBrandToDatabase", "Storing Brand to DB: $brandToSave")
        return brandId
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
    }
}


