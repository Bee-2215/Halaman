package com.example.halaman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Data class untuk item dalam daftar
data class Item(
    val name: String,
    val description: String,
    val imageRes: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val sampleItems = listOf(
                    Item("Tumbler Hijau", "Terakhir kali saya memakai tumbler ini adalah sekitar jam 12.50 di FEB, selepas sholat dhuhr dan hendak menuju kelas.", R.drawable.sample_image1),
                    Item("Kacamata Anti Radiasi", "Minggu ini kuliahkan di R...", R.drawable.sample_image2),
                    Item("Rolex Terbaru", "Kulitnya warna cokelat dan...", R.drawable.sample_image3),
                    Item("Converse Pink Muda", "Ukuran 38, ada coretan...", R.drawable.sample_image4),
                    Item("Tas Rajut Putih", "Buat valentine tapi putus...", R.drawable.sample_image5)
                )
                AppNavigation(sampleItems)
            }
        }
    }
}

@Composable
fun AppNavigation(items: List<Item>) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(items = items, navController = navController)
        }
        composable("detail/{itemName}") { backStackEntry ->
            val itemName = backStackEntry.arguments?.getString("itemName")
            val item = items.find { it.name == itemName }
            item?.let { DetailScreen(item, navController) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(items: List<Item>, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "HALAMAN",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF6200EE)) {
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = android.R.drawable.ic_menu_view), contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = android.R.drawable.ic_menu_mapmode), contentDescription = "Map") },
                    label = { Text("Map") },
                    selected = false,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = android.R.drawable.ic_menu_report_image), contentDescription = "Report") },
                    label = { Text("Report") },
                    selected = false,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = android.R.drawable.ic_menu_info_details), contentDescription = "Status") },
                    label = { Text("Status") },
                    selected = false,
                    onClick = {}
                )
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(items) { item ->
                ItemCard(item, navController)
            }
        }
    }
}

@Composable
fun ItemCard(item: Item, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = "Gambar untuk ${item.name}",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Nama Barang: ${item.name}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Deskripsi: ${item.description}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            TextButton(onClick = { navController.navigate("detail/${item.name}") }) {
                Text(text = "Lihat")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(item: Item, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail Barang") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_media_previous),
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = "Gambar detail untuk ${item.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = item.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.description,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val sampleItems = listOf(
        Item("Tumbler Hijau", "Terakhir kali saya memakai tumbler ini adalah sekitar jam 12.50 di FEB, selepas sholat dhuhr dan hendak menuju kelas.", android.R.drawable.ic_menu_gallery),
        Item("Kacamata Anti Radiasi", "Minggu ini kuliahkan di R...", android.R.drawable.ic_menu_camera),
        Item("Rolex Terbaru", "Kulitnya warna cokelat dan...", android.R.drawable.ic_menu_slideshow)
    )
    val navController = rememberNavController()
    HomeScreen(items = sampleItems, navController = navController)
}