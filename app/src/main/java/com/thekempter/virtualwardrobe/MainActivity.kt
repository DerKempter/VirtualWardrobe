package com.thekempter.virtualwardrobe

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.thekempter.virtualwardrobe.ui.theme.VirtualWardrobeTheme
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
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

    fun getAllClothings(): LiveData<List<ClothingItem>> {
        return clothingViewModel.clothings
    }

    fun getSeasonsForClothing(item: ClothingItem): LiveData<List<Season>> {
        return clothingViewModel.getSeasonsForClothingId(item)
    }

    fun getClothingTypeForClothing(item: ClothingItem): LiveData<ClothingType> {
        return clothingViewModel.getClothingTypeForClothingId(item)
    }

    fun getAllSeasons(): LiveData<List<Season>> {
        return clothingViewModel.getAllSeasons()
    }

    @Composable
    fun HomeScreen(){

    }

    @Composable
    fun CollectionScreen(){
        val clothingsLiveData = getAllClothings()

        Column() {
            val clothings = clothingsLiveData.value ?: emptyList()
            clothings.forEach { clothingItem ->
                val seasons = getSeasonsForClothing(clothingItem)
                val allSeasons = getAllSeasons()
                val clothingType = getClothingTypeForClothing(clothingItem)

                if (seasons.value != null || clothingType.value != null){
                    val seasonsVal = seasons.value!!
                    val clothingTypeVal = clothingType.value!!
                    ClothingItemDisplay(clothingItem, seasonsVal, clothingTypeVal)
                }
            }
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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Settings",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            SettingItem(label = "Manage Clothing Types", value = "Add or remove clothing types here")
        }
    }


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
    fun SettingItem(label: String, value: String) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = value)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        VirtualWardrobeTheme {
            val testClothingItem = ClothingItem(
                0,
                "Blaue Hose",
                0,
                "blue",
                "NewYorker",
                "10",
                "cotton",
                "null"
            )
            val testSeasons = mutableListOf<Season>(Season(0,"Spring"), Season(1,"Summer"), Season(2,"Autumn"))
            val testAllSeasons = mutableListOf<Season>(Season(0,"Spring"), Season(1,"Summer"), Season(2,"Autumn"), Season(3,"Winter"), Season(4,"Halloween"))
            val testClothingType = ClothingType(0, "Jeans")
            ClothingItemDisplay(clothingItem = testClothingItem, seasons = testSeasons, clothingType = testClothingType)
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
