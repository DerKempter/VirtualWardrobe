package com.thekempter.virtualwardrobe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.thekempter.virtualwardrobe.data.ClothingItem
import com.thekempter.virtualwardrobe.data.ClothingType
import com.thekempter.virtualwardrobe.data.Season
import com.thekempter.virtualwardrobe.data.WardrobeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import java.lang.IllegalArgumentException

class ClothingViewModel(private val repository: WardrobeRepository = Graph.wardrobeRepo) :
    ViewModel() {

    private val _state = MutableStateFlow(ClothingViewState())
    val state: StateFlow<ClothingViewState>
        get() = _state

    val clothes = repository.allClothes
    val clothingTypes = repository.allTypes

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

    fun getSeasonsForClothingId(clothingId: Int, callback: (List<Season>) -> Unit) = viewModelScope.launch {
        val result = repository.getSeasonsByClothingId(clothingId)
        callback(result)
    }

//    fun getClothingTypeForClothingId(typeId: Int, callback: (ClothingType) -> Unit) = viewModelScope.launch {
//        val result = repository.getClothingTypeByClothing(typeId)
//        callback(result)
//    }

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
    val clothingTypes: List<ClothingType> = emptyList()
)