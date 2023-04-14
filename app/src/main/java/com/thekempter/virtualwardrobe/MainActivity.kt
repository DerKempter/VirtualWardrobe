package com.thekempter.virtualwardrobe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.thekempter.virtualwardrobe.ui.theme.VirtualWardrobeTheme
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val items = listOf(
                BottomNavigationItem(
                    icon = Icons.Rounded.Home,
                    name = "Home",
                    route = "home",
                ),
                BottomNavigationItem(
                    icon = Icons.Rounded.Build,
                    name = "Collection",
                    route = "collection",
                ),
                BottomNavigationItem(
                    icon = Icons.Rounded.Create,
                    name = "Outfits",
                    route = "outfits",
                ),
                BottomNavigationItem(
                    icon = Icons.Rounded.Settings,
                    name = "Settings",
                    route = "settings",
                )
            )
            VirtualWardrobe(navController = navController, items = items)
        }
    }
}

data class BottomNavigationItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VirtualWardrobe(navController: NavHostController, items: List<BottomNavigationItem>){
    val backStackEntry = navController.currentBackStackEntryAsState()
    VirtualWardrobeTheme {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    items.forEach{ item ->
                        val selected = item.route == backStackEntry.value?.destination?.route

                        NavigationBarItem(
                            selected = selected,
                            onClick = { navController.navigate(item.route) },
                            label = {
                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.SemiBold
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = "${item.name} Icon"
                                )
                            }
                        )
                    }
                }
            },
            content = {
                NavHost(navController = navController, startDestination = items.first().route){
                    items.forEach{ item ->
                        composable(item.route) {
                            when(item.route) {
                                "home" -> HomeScreen()
                                "collection" -> CollectionScreen()
                                "outfits" -> OutfitScreen()
                                "settings" -> SettingsScreen()
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun HomeScreen(){
    Column() {
        ClothingItem("Blue Jeans", "Classic denim jeans")
        ClothingItem("Red T-shirt", "Soft cotton T-shirt")
        ClothingItem("Black Sneakers", "Sleek and stylish sneakers")
    }
}

@Composable
fun CollectionScreen(){
    Column() {
        ClothingItem("Red T-shirt", "Soft cotton T-shirt")
    }
}

@Composable
fun OutfitScreen(){
    Column() {
        ClothingItem("Black Sneakers", "Sleek and stylish sneakers")
    }
}

@Composable
fun SettingsScreen(){
    Column() {
        ClothingItem("Blue-ish Jeans", "Classic denim jeans")
    }
}


@Composable
fun ClothingItem(itemName: String, itemDescription: String) {
    Surface(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = itemName, style = MaterialTheme.typography.headlineMedium)
            Text(text = itemDescription, style = MaterialTheme.typography.bodyMedium)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VirtualWardrobeTheme {
        HomeScreen()
    }
}
