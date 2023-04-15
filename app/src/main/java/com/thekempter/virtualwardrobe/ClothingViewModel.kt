package com.thekempter.virtualwardrobe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClothingViewModel(
    private val repository: WardrobeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _clothings = MutableLiveData<List<ClothingItem>>()
    val clothings: LiveData<List<ClothingItem>>
        get() = _clothings

    init {
        loadClothings()
    }

    private fun loadClothings() {
        scope.launch {
            try {
                val clothingsList = withContext(Dispatchers.IO) {
                    repository.getAllClothes()
                }
                _clothings.value = clothingsList
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun getSeasonsForClothingId(clothingItem: ClothingItem): MutableLiveData<List<Season>> {
        val seasonsLiveData = MutableLiveData<List<Season>>()
        scope.launch {
            val seasons = repository.getSeasonsByClothingId(clothingItem)
            seasonsLiveData.postValue(seasons)
        }
        return seasonsLiveData
    }

    fun getAllSeasons(): MutableLiveData<List<Season>> {
        val seasonsLiveData = MutableLiveData<List<Season>>()
        scope.launch {
            val seasons = repository.getAllSeasons()
            seasonsLiveData.postValue(seasons)
        }
        return seasonsLiveData
    }

    fun getAllTypes(): MutableLiveData<List<ClothingType>> {
        val clothingTypesLiveData = MutableLiveData<List<ClothingType>>()
        scope.launch {
            val clothingTypes = repository.getAllClothingTypes()
            clothingTypesLiveData.postValue(clothingTypes)
        }
        return clothingTypesLiveData
    }

    fun getClothingTypeForClothingId(clothingItem: ClothingItem): MutableLiveData<ClothingType> {
        val clothingTypeLiveData = MutableLiveData<ClothingType>()
        scope.launch {
            val clothingType = repository.getClothingTypeByClothing(clothingItem)
            clothingTypeLiveData.postValue(clothingType)
        }
        return clothingTypeLiveData
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val savedStateHandle = extras.createSavedStateHandle()

                return ClothingViewModel(
                    (application as VirtualWardrobeApplication).repository,
                    savedStateHandle
                ) as T
            }
        }
    }
}