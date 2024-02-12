package com.thekempter.virtualwardrobe.ui.components

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.thekempter.virtualwardrobe.ClothingViewModel
import com.thekempter.virtualwardrobe.ClothingViewState
import com.thekempter.virtualwardrobe.data.Brand
import com.thekempter.virtualwardrobe.data.ClothingItem
import com.thekempter.virtualwardrobe.data.ClothingType
import com.thekempter.virtualwardrobe.data.Season
import com.thekempter.virtualwardrobe.data.util.getImageForClothingById
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClothingItemDisplay(
    clothingItem: ClothingItem,
    seasons: List<Season>,
    clothingType: ClothingType,
    brand: Brand,
    clothingViewModel: ClothingViewModel,
    navController: NavController
) {
    seasons.map { it.name }.distinct()
    Box(modifier = Modifier.fillMaxWidth()){
        Surface(modifier = Modifier.fillMaxWidth(),
            onClick = {
                clothingViewModel.currentClothingItem = clothingItem
                navController.navigate("itemDetail/${clothingItem.id}")
            }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = clothingItem.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(text = clothingItem.color, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.padding(6.dp))
                    Text(text = clothingItem.material, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.padding(6.dp))
                    Text(text = brand.name, style = MaterialTheme.typography.bodyMedium)
                }
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(
                        text = "Size " + clothingItem.size,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                    Text(text = clothingType.name, style = MaterialTheme.typography.bodyMedium)
                }
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    seasons.forEach { season ->
                        Text(text = season.name, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.padding(6.dp))
                    }
                }
            }
        }
        Log.d("ClothingItemDisplay", "imageUrl = ${clothingItem.imageId}")
        val clothingImageList = getImageForClothingById(clothingViewModel, clothingItem.imageId)
        if(clothingImageList.value.size == 1){
            LoadImageFromUriWithCoil(imageData = clothingImageList.value.first().bitData)
        }
    }
}

@Composable
fun ClothingItemDetailView(clothingItem: ClothingItem, clothingViewModel: ClothingViewModel, clothingType: ClothingType, brand: Brand) {
    //val allSeasonsStrings = allSeasons.map { it.name }
    // var selectedItems = seasons.map { it.name }.toSet()
    Surface(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            val clothingImageList = getImageForClothingById(clothingViewModel = clothingViewModel, clothingId = clothingItem.id)
            if(clothingImageList.value.size == 1){
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    LoadImageFromUriWithCoil(imageData = clothingImageList.value.first().bitData, 800)
                }
            }
            Text(text = clothingItem.name, style = MaterialTheme.typography.headlineMedium)
            Text(text = clothingItem.color, style = MaterialTheme.typography.bodyMedium)
            Text(text = clothingItem.material, style = MaterialTheme.typography.bodyMedium)
            Text(text = brand.name, style = MaterialTheme.typography.bodyMedium)
            Text(text = clothingItem.size, style = MaterialTheme.typography.bodyMedium)
            Text(text = clothingType.name, style = MaterialTheme.typography.bodyMedium)
//            MultiSelect(
//                items = allSeasonsStrings,
//                selectedItems = seasons.map { it.name }.toSet(),
//                onItemSelected = {
//                        item -> selectedItems = selectedItems.plus(item)
//                },
//                onItemDeselected = {
//                        item -> selectedItems = selectedItems.minus(item)
//                }
//            )
        }
    }

}

@Composable
fun MultiSelect(
    items: List<String>,
    selectedItems: Set<String>,
    onItemSelected: (String) -> Unit,
    onItemDeselected: (String) -> Unit
) {
    Column {
        items.forEach { item ->
            Row(
                Modifier.clickable {
                    if (selectedItems.contains(item)) {
                        onItemDeselected(item)
                    } else {
                        onItemSelected(item)
                    }
                }
            ) {
                Checkbox(
                    checked = selectedItems.contains(item),
                    onCheckedChange = {
                        if (it) {
                            onItemSelected(item)
                        } else {
                            onItemDeselected(item)
                        }
                    }
                )
                Text(text = item)
            }
        }
    }
}

@Composable
fun DropdownMenuWithOutlinedTextFieldBase(state: ClothingViewState, type: MutableState<ClothingType>? = null, brand: MutableState<Brand>? = null){
    if (type != null) DropdownMenuWithOutlinedTextFieldClothingType(state, type)
    if (brand != null) DropdownMenuWithOutlinedTextFieldBrand(state, brand)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuWithOutlinedTextFieldClothingType(state: ClothingViewState, type: MutableState<ClothingType>){
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { type }
    var types by remember { mutableStateOf(state.clothingTypes) }
    OutlinedTextField(
        value = selectedOption.name,
        label = { Text(text = "Type") },
        onValueChange = { query ->
            types = if (query.isEmpty()) state.clothingTypes
            else state.clothingTypes.filter { it.name.lowercase().contains(query.lowercase()) }
            expanded = types.isNotEmpty()
            selectedOption = ClothingType(name = query)
        },
        modifier = Modifier
            .clickable(
                onClick = { expanded = true }
            )
            .fillMaxWidth(),
        enabled = true,
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        trailingIcon = { IconButton(onClick = { expanded = !expanded }) {
            Icon(imageVector = Icons.Rounded.ArrowDropDown, contentDescription = "Expand Dropdown")
        }}
    )
    AnimatedVisibility(visible = expanded) {
        Card(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .height(minOf(types.size * 45, 150).dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            border = BorderStroke(1.dp, Color.Black),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
            ) {
                items(items = types) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedOption = it; expanded = !expanded }
                            .padding(10.dp)
                    ) {
                        Text(text = it.name)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuWithOutlinedTextFieldBrand(state: ClothingViewState, brand: MutableState<Brand>){
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { brand }
    var brands by remember { mutableStateOf(state.brands) }
    OutlinedTextField(
        value = selectedOption.name,
        label = { Text(text = "Brand") },
        onValueChange = { query ->
            Log.d("BrandDropdownMenu", "avail brands: $brands")
            brands = state.brands.filter { it.name.contains(query) }
            expanded = brands.isNotEmpty()
            selectedOption = Brand(name = query)
        },
        modifier = Modifier
            .clickable(
                onClick = { expanded = true }
            )
            .fillMaxWidth(),
        enabled = true,
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        trailingIcon =  {IconButton(onClick = { expanded = !expanded }) {
            Icon(imageVector = Icons.Rounded.ArrowDropDown, contentDescription = "Expand Dropdown")
        }}
    )
    AnimatedVisibility(visible = expanded) {
        Card(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .height(minOf(brands.size * 45, 150).dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            border = BorderStroke(1.dp, Color.Black),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
            ) {
                items(items = brands) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedOption = it; expanded = !expanded }
                            .padding(10.dp)
                    ) {
                        Text(text = it.name)
                    }
                }
            }
        }
    }
}

@Composable
fun LoadImageFromUriWithCoil(imageData: ByteArray, size: Int = 300) {
    val context = LocalContext.current
    val bitmap = remember(imageData) {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeByteArray(imageData, 0, imageData.size, this)
        }
        val scaleFactor = max(options.outWidth, options.outHeight)/size

        BitmapFactory.decodeByteArray(imageData, 0, imageData.size, options.apply {
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        })
    }

    AsyncImage(
        model = ImageRequest.Builder(context).data(bitmap).crossfade(true).build(),
        contentDescription = null,
        placeholder = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.clip(CircleShape)
    )
}