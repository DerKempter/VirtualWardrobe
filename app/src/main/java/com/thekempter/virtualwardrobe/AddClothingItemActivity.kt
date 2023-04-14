package com.thekempter.virtualwardrobe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.thekempter.virtualwardrobe.ui.theme.VirtualWardrobeTheme

class AddClothingItemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddClothingItemScreen {}
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddClothingItemScreen(onBackPressed: () -> Unit) {
    val name = remember { mutableStateOf("") }
    val type = remember { mutableStateOf("") }
    val color = remember { mutableStateOf("") }
    val brand = remember { mutableStateOf("") }
    val size = remember { mutableStateOf("") }
    val material = remember { mutableStateOf("") }
    val imageUrl = remember { mutableStateOf("") }

    VirtualWardrobeTheme{
        Scaffold(
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (imageUrl.value.isNotEmpty()){
                        Image(
                            painter = rememberAsyncImagePainter(model = imageUrl.value),
                            contentDescription = name.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(shape = RoundedCornerShape(4.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    else{
                        Image(
                            painter = rememberAsyncImagePainter(model = ""),
                            contentDescription = name.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(shape = RoundedCornerShape(4.dp)),
                            contentScale = ContentScale.Crop
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
                            onBackPressed()
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AddClothingItemScreen {}
}