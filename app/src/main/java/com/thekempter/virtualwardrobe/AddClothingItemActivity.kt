package com.thekempter.virtualwardrobe

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.platform.LocalContext
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
        val scrollState = rememberScrollState()
        val context = LocalContext.current

        val launcherGallery = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()){
                uri ->
            if(uri.toString() == "null"){
                imageUrl.value = imageUrl.value
            }
            else{
                imageUrl.value = uri.toString()
            }
        }

        val imageUri: Uri = remember {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "picture.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }
            // Insert a new empty image into the MediaStore
            val contentResolver = context.contentResolver
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
            // TODO make it so image is not saved in gallery aka find a way for the picture to not appear in gallery
        }
        val launcherCamera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                // Update the imageUri with the new picture taken
                imageUrl.value = imageUri.toString()
            }
        }


        VirtualWardrobeTheme{
            Scaffold(
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = imageUrl.value),
                                contentDescription = name.value,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.TopCenter)
                                    .height((400 - scrollState.value).coerceAtLeast(150).dp)
                                    .clip(shape = RoundedCornerShape(8.dp))
                                    .clickable {
                                        val options =
                                            arrayOf("Take Photo", "Choose from Gallery", "Cancel")
                                        val builder = AlertDialog.Builder(context)
                                        builder.setTitle("Select an Option")
                                        builder.setItems(options) { dialog, which ->
                                            when (which) {
                                                0 -> {
                                                    launcherCamera.launch(imageUri)
                                                }
                                                1 -> {
                                                    launcherGallery.launch(
                                                        PickVisualMediaRequest(
                                                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                                        )
                                                    )
                                                }
                                                else -> dialog.dismiss()
                                            }
                                        }
                                        builder.show()
                                    },
                                contentScale = ContentScale.Crop
                            )
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .padding(bottom = 40.dp)
                                .padding(top = (400 - scrollState.value).coerceAtLeast(150).dp)
                                .verticalScroll(scrollState)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
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
                        }
                        Spacer(
                            modifier = Modifier.height(32.dp)
                        )
                        Button(
                            onClick = {
                                // Save the data and go back to previous screen
                                //onBackPressed()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
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


