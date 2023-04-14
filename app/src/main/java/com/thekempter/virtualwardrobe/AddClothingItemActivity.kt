package com.thekempter.virtualwardrobe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.thekempter.virtualwardrobe.ui.theme.VirtualWardrobeTheme

class AddClothingItemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddClothingItemScreen(name, type, color, brand, size, material, imageUrl)
        }
    }

    var name = mutableStateOf("")
    var type = mutableStateOf("")
    var color = mutableStateOf("")
    var brand = mutableStateOf("")
    var size = mutableStateOf("")
    var material = mutableStateOf("")
    var imageUrl = mutableStateOf("")

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddClothingItemScreen(
        name: MutableState<String>,
        type: MutableState<String>,
        color: MutableState<String>,
        brand: MutableState<String>,
        size: MutableState<String>,
        material: MutableState<String>,
        imageUrl: MutableState<String>,
        ) {

        val launcherWithPrevImage = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()){
                uri ->
            if(uri.toString() == "null"){
                imageUrl.value = imageUrl.value
            }
            else{
                imageUrl.value = uri.toString()
                name.value = uri.toString()
            }
        }


        VirtualWardrobeTheme{
            Scaffold(
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (imageUrl.value.isNotEmpty()){
                            Image(
                                painter = rememberAsyncImagePainter(model = imageUrl.value),
                                contentDescription = name.value,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp)
                                    .clip(shape = RoundedCornerShape(4.dp))
                                    .clickable {
                                        launcherWithPrevImage.launch(
                                            PickVisualMediaRequest(
                                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
                                    },
                                contentScale = ContentScale.Crop
                            )
                        }
                        else{
                            Image(
                                painter = rememberAsyncImagePainter(model = ""),
                                contentDescription = name.value,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp)
                                    .clip(shape = RoundedCornerShape(4.dp))
                                    .clickable {
                                        launcherWithPrevImage.launch(
                                            PickVisualMediaRequest(
                                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
                                    },
                                contentScale = ContentScale.Crop,

                                )
                        }
                        OutlinedTextField(
                            value = name.value,
                            onValueChange = { name.value = it },
                            label = { Text("Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = type.value,
                            onValueChange = { type.value = it },
                            label = { Text("Type") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = color.value,
                            onValueChange = { color.value = it },
                            label = { Text("Color") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = brand.value,
                            onValueChange = { brand.value = it },
                            label = { Text("Brand") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = size.value,
                            onValueChange = { size.value = it },
                            label = { Text("Size") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = material.value,
                            onValueChange = { material.value = it },
                            label = { Text("Material") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(
                            onClick = {
                                // Save the data and go back to previous screen
                                //onBackPressed()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Save")
                        }
                    }
                }
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        AddClothingItemScreen(
            remember { mutableStateOf("") },
            remember { mutableStateOf("") },
            remember { mutableStateOf("") },
            remember { mutableStateOf("") },
            remember { mutableStateOf("") },
            remember { mutableStateOf("") },
            remember { mutableStateOf("") }
        )
    }
}


