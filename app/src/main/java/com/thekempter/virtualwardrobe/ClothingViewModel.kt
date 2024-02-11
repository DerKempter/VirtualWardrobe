package com.thekempter.virtualwardrobe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.thekempter.virtualwardrobe.data.Brand
import com.thekempter.virtualwardrobe.data.ClothingItem
import com.thekempter.virtualwardrobe.data.ClothingType
import com.thekempter.virtualwardrobe.data.ClothingImage
import com.thekempter.virtualwardrobe.data.Season
import com.thekempter.virtualwardrobe.data.WardrobeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ClothingViewModel(private val repository: WardrobeRepository = Graph.wardrobeRepo) :
    ViewModel() {

    private val _state = MutableStateFlow(ClothingViewState())
    val state: StateFlow<ClothingViewState>
        get() = _state

    private val clothes = repository.allClothes
    private val clothingTypes = repository.allTypes
    var currentClothingItem: ClothingItem = ClothingItem(id = -1, name = "", typeId = -1, color = "", brandId = -1, size = "", material = "", imageId = -1)

    init {
        viewModelScope.launch {
            combine(clothes, clothingTypes) { clothes: List<ClothingItem>, clothingTypes: List<ClothingType> ->
                ClothingViewState(clothes, clothingTypes)
            }.collect {
                _state.value = it
            }
        }
    }

    fun addClothingItem(clothingItem: ClothingItem) = viewModelScope.launch {
        repository.addClothing(clothingItem)
    }

    fun addClothingType(clothingType: ClothingType) = viewModelScope.launch {
        repository.addClothingType(clothingType)
    }

    suspend fun addClothingImage(clothingImage: ClothingImage) = repository.addClothingImage(clothingImage)

    suspend fun addBrand(brand: Brand) = repository.addBrand(brand)

    fun getSeasonsForClothingId(clothingId: Int, callback: (List<Season>) -> Unit) = viewModelScope.launch {
        val result = repository.getSeasonsByClothingId(clothingId)
        callback(result)
    }

    suspend fun getImageForClothingById(imageId: Int) = repository.getImageForClothingById(imageId)

    fun getLatestImage(callback: (List<ClothingImage>) -> Unit) = viewModelScope.launch {
        val result = repository.getLatestImage()
        callback(result)
    }

    suspend fun getLatestImage() = repository.getLatestImage()

}

@Suppress("UNCHECKED_CAST")
class ClothingViewModelFactory(private val repository: WardrobeRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClothingViewModel::class.java)){
            return ClothingViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown view model class")
        }
    }
}

data class ClothingViewState(
    val clothes: List<ClothingItem> = emptyList(),
    val clothingTypes: List<ClothingType> = emptyList(),
    val brands: List<Brand> = emptyList()
)