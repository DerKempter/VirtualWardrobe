package com.thekempter.virtualwardrobe

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState


class MainActivity : ComponentActivity() {

    private val clothingViewModel: ClothingViewModel
        get() {
            val clothingViewModel: ClothingViewModel by viewModels { ClothingViewModel.Factory }
            return clothingViewModel
        }

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
                ),
                FloatingNavigationItem(
                    icon = Icons.Rounded.Add,
                    name = "Add New Clothing",
                    route = "addNewClothingItem"
                ),
                FloatingNavigationItem(
                    icon = Icons.Rounded.Add,
                    name = "Add New Outfit",
                    route = "addNewOutfitItem"
                )
            )
            VirtualWardrobe(navController = navController, items = items)
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun VirtualWardrobe(navController: NavHostController, items: List<NavigationItem>){
        val backStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry.value?.destination?.route
        VirtualWardrobeTheme {
            Scaffold(
                bottomBar = {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.background
                    ) {
                        items.forEach{ item ->
                            if (item is BottomNavigationItem){
                                val selected = item.route == currentRoute

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
                    }
                },
                floatingActionButton = {
                    if(currentRoute == "collection"){
                        FloatingActionButton(
                            onClick = {
                                val intent =
                                    Intent(this@MainActivity, AddClothingItemActivity::class.java)
                                startActivity(intent)
                            },
                            content = {
                                val item: FloatingNavigationItem =
                                    items.filterIsInstance<FloatingNavigationItem>().first { fni ->
                                        fni.route == "addNewClothingItem"
                                    }
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = "${item.name} Icon"
                                )
                            }
                        )
                    }
                    else if (currentRoute == "outfits"){
                        FloatingActionButton(
                            onClick = {
                                val intent =
                                    Intent(this@MainActivity, AddOutfitActivity::class.java)
                                startActivity(intent)
                            },
                            content = {
                                val item: FloatingNavigationItem =
                                    items.filterIsInstance<FloatingNavigationItem>().first { fni ->
                                        fni.route == "addNewOutfitItem"
                                    }
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = "${item.name} Icon"
                                )
                            }
                        )
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

    fun getAllClothings(): List<ClothingItem> {
        return clothingViewModel.clothings.value ?: emptyList()
    }

    @Composable
    fun HomeScreen(){
        val clothings = getAllClothings()

        val column = Column() {
            clothings.forEach { clothingItem -> 
                ClothingItemDisplay(clothingItem = clothingItem)
            }
        }
    }

    @Composable
    fun CollectionScreen(){
        Column() {
//        ClothingItemDisplay("Red T-shirt", "Soft cotton T-shirt")
        }
    }

    @Composable
    fun OutfitScreen(){
        Column() {
//        ClothingItemDisplay("Black Sneakers", "Sleek and stylish sneakers")
        }
    }

    @Composable
    fun SettingsScreen(){
        Column() {
//        ClothingItemDisplay("Blue-ish Jeans", "Classic denim jeans")
        }
    }

    @Composable
    fun AddClothingItemScreen(){

    }


    @Composable
    fun ClothingItemDisplay(clothingItem: ClothingItem) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = clothingItem.name, style = MaterialTheme.typography.headlineMedium)
                Text(text = clothingItem.color, style = MaterialTheme.typography.bodyMedium)
                Text(text = clothingItem.material, style = MaterialTheme.typography.bodyMedium)
                Text(text = clothingItem.brand, style = MaterialTheme.typography.bodyMedium)
                Text(text = clothingItem.size, style = MaterialTheme.typography.bodyMedium)
                Text(text = clothingItem.type, style = MaterialTheme.typography.bodyMedium)
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
}

open class NavigationItem(
    open val name: String,
    open val route: String,
    open val icon: ImageVector
)

data class BottomNavigationItem(
    override val name: String,
    override val route: String,
    override val icon: ImageVector) : NavigationItem(name, route, icon)

data class FloatingNavigationItem(
    override val name: String,
    override val route: String,
    override val icon: ImageVector) : NavigationItem(name, route, icon)
