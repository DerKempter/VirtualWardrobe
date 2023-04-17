package com.thekempter.virtualwardrobe.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.thekempter.virtualwardrobe.ClothingViewState
import com.thekempter.virtualwardrobe.data.ClothingItem
import com.thekempter.virtualwardrobe.data.ClothingType
import com.thekempter.virtualwardrobe.data.Season

@Composable
fun ClothingItemDisplay(clothingItem: ClothingItem, seasons: List<Season>, clothingType: ClothingType) {
    seasons.map { it.name }.toSet()
    Surface(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(modifier = Modifier.align(Alignment.CenterHorizontally), text = clothingItem.name, style = MaterialTheme.typography.headlineMedium)
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = clothingItem.color, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.padding(6.dp))
                Text(text = clothingItem.material, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.padding(6.dp))
                Text(text = clothingItem.brand, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Size " + clothingItem.size, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.padding(6.dp))
                Text(text = clothingType.name, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                seasons.forEach {season ->
                    Text(text = season.name, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.padding(6.dp))
                }
            }
        }
    }
}

@Composable
fun ClothingItemDetailView(clothingItem: ClothingItem, seasons: List<Season>, allSeasons: List<Season>, clothingType: ClothingType) {
    val allSeasonsStrings = allSeasons.map { it.name }
    var selectedItems = seasons.map { it.name }.toSet()
    Surface(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = clothingItem.name, style = MaterialTheme.typography.headlineMedium)
            Text(text = clothingItem.color, style = MaterialTheme.typography.bodyMedium)
            Text(text = clothingItem.material, style = MaterialTheme.typography.bodyMedium)
            Text(text = clothingItem.brand, style = MaterialTheme.typography.bodyMedium)
            Text(text = clothingItem.size, style = MaterialTheme.typography.bodyMedium)
            Text(text = clothingType.name, style = MaterialTheme.typography.bodyMedium)
            MultiSelect(
                items = allSeasonsStrings,
                selectedItems = seasons.map { it.name }.toSet(),
                onItemSelected = {
                        item -> selectedItems = selectedItems.plus(item)
                },
                onItemDeselected = {
                        item -> selectedItems = selectedItems.minus(item)
                }
            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuWithOutlinedTextFieldBase(state: ClothingViewState, type: MutableState<ClothingType>){
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(type.value) }
    OutlinedTextField(
        value = selectedOption.name,
        label = { Text(text = "Type") },
        onValueChange = {},
        modifier = Modifier
            .clickable(
                onClick = {
                    expanded = !expanded
                }
            )
            .fillMaxSize(),
        enabled = false,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = !expanded },
        properties = PopupProperties(focusable = true),
    ) {
        state.clothingTypes.forEach { type ->
            DropdownMenuItem(
                onClick = {
                    selectedOption = type
                    expanded = !expanded
                },
                text = { Text(text = type.name) }
            )
        }

    }
}