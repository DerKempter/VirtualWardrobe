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