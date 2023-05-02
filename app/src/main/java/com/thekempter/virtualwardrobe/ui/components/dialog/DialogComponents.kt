package com.thekempter.virtualwardrobe.ui.components.dialog

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.thekempter.virtualwardrobe.ClothingViewModel
import com.thekempter.virtualwardrobe.ClothingViewModelFactory
import com.thekempter.virtualwardrobe.ClothingViewState
import com.thekempter.virtualwardrobe.Graph
import com.thekempter.virtualwardrobe.data.ClothingType
import com.thekempter.virtualwardrobe.ui.components.DropdownMenuWithOutlinedTextFieldBase
import com.thekempter.virtualwardrobe.ui.theme.VirtualWardrobeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddClothingItemUi(
    clothingViewModel: ClothingViewModel,
    onSaveButtonClicked: (ClothingViewModel, Uri) -> Unit,
    addDefaultClothingTypes: (ClothingViewModel) -> Unit,
    state: ClothingViewState,
    name: MutableState<String>,
    color: MutableState<String>,
    imageUrl: MutableState<Uri>,
    brand: MutableState<String>,
    size: MutableState<String>,
    material: MutableState<String>,
    type: MutableState<ClothingType>,
    scrollState: ScrollState,
    context: Context,
    launcherGallery: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    launcherCamera: ManagedActivityResultLauncher<Uri, Boolean>,
    imageUri: Uri,
    imageBitmap: MutableState<Bitmap>,
    permissionLauncher: ActivityResultLauncher<String>,
    permissionGranted: MutableState<Boolean>
    ){
    VirtualWardrobeTheme{
        Scaffold(
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    ImageBuilderWithPermission(imageBitmap,
                        name,
                        imageUri,
                        launcherGallery,
                        launcherCamera,
                        scrollState,
                        context,
                        permissionLauncher,
                        permissionGranted
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
                        DropdownMenuWithOutlinedTextFieldBase(state = state, type = type)
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
                            onSaveButtonClicked(clothingViewModel, imageUrl.value)
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
        Log.d("virtualWardrobe", state.clothingTypes.toString())
        val coroutineScope = rememberCoroutineScope()
        val dialogState = remember { mutableStateOf(false) }
        if(!dialogState.value){
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    delay(1000)
                    dialogState.value = true
                }
            }
        }
        if (state.clothingTypes.isEmpty()) {
            if (dialogState.value) {
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text(text = "No Types found") },
                    text = { Text(text = "Do you want to add a few default Clothing Types automatically?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                addDefaultClothingTypes(clothingViewModel)
                                dialogState.value = false
                            },
                            content = { Text(text = "Confirm") }
                        )
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                dialogState.value = false
                                Toast.makeText(
                                    context,
                                    "Add your custom Types from the settings",
                                    Toast.LENGTH_LONG
                                ).show()
                            },
                            content = { Text(text = "Cancel") }
                        )
                    },
                    shape = RoundedCornerShape(22.dp),
                    containerColor = MaterialTheme.colorScheme.background,
                    textContentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.background)
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddClothingItemScreen(
    onSaveButtonClicked: (ClothingViewModel, Uri) -> Unit,
    addDefaultClothingTypes: (ClothingViewModel) -> Unit,
    name: MutableState<String>,
    type: MutableState<ClothingType>,
    color: MutableState<String>,
    brand: MutableState<String>,
    size: MutableState<String>,
    material: MutableState<String>,
    imageUrl: MutableState<Uri>,
    imageBitmap: MutableState<Bitmap>,
    permissionLauncher: ActivityResultLauncher<String>,
    permissionGranted: MutableState<Boolean>
) {
    val clothingViewModel = viewModel(
        ClothingViewModel::class.java,
        factory = ClothingViewModelFactory(Graph.wardrobeRepo)
    )
    val state by clothingViewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Log.d("AddClothingActivity", "value of allTypes: ${state.clothingTypes}")

    try {
        type.value = state.clothingTypes.first()
    } catch (e: NoSuchElementException){
        type.value = ClothingType(-1, "")
    }


    val launcherGallery = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()){
            uri ->
        if(uri == null){
            imageUrl.value = imageUrl.value
        }
        else{
            imageUrl.value = uri
            Log.d("AddClothingItemScreen", "picked Uri from photoPicker: $uri")

            // Get the Bitmap from the Uri
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            val bitmap = ImageDecoder.decodeBitmap(source)

            imageBitmap.value = bitmap
        }
    }

    val imageUri: Uri = remember {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val currentDateTimeFormatted = currentDateTime.format(formatter)
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$currentDateTimeFormatted.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        }
        // Insert a new empty image into the MediaStore
        val contentResolver = context.contentResolver
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
    }

    val launcherCamera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        Log.d("launcherCamera", "success = $success")
        if (success) {
            // Get the Bitmap from the imageUri
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            val bitmap = ImageDecoder.decodeBitmap(source)

            imageBitmap.value = bitmap
            Log.d("launcherCamera", "bitmap length = ${bitmap.byteCount}")
            Log.d("launcherCamera", "imageBitmap length = ${imageBitmap.value.byteCount}")

            // Delete the image if it was taken with the camera
            imageUrl.value = imageUri
            context.contentResolver.delete(imageUri, null, null)
        }
    }

    AddClothingItemUi(
        clothingViewModel,
        onSaveButtonClicked,
        addDefaultClothingTypes,
        state,
        name,
        color,
        imageUrl,
        brand,
        size,
        material,
        type,
        scrollState,
        context,
        launcherGallery,
        launcherCamera,
        imageUri,
        imageBitmap,
        permissionLauncher,
        permissionGranted
    )
}

@Composable
fun ImageBuilderWithPermission(
    imageBitmap: MutableState<Bitmap>,
    name: MutableState<String>,
    imageUri: Uri,
    launcherGallery: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    launcherCamera: ManagedActivityResultLauncher<Uri, Boolean>,
    scrollState: ScrollState,
    context: Context,
    permissionLauncher: ActivityResultLauncher<String>,
    permissionGranted: MutableState<Boolean>
){
    when (PackageManager.PERMISSION_GRANTED) {
        context.checkSelfPermission(
            Manifest.permission.CAMERA
        ) -> {
            Image(
                painter = rememberAsyncImagePainter(model = imageBitmap.value),
                contentDescription = name.value,
                modifier = Modifier
                    .fillMaxWidth()
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
        }
        else -> {
            permissionLauncher.launch(Manifest.permission.CAMERA)
            if (permissionGranted.value) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageBitmap.value),
                    contentDescription = name.value,
                    modifier = Modifier
                        .fillMaxWidth()
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
            }
        }
    }

}