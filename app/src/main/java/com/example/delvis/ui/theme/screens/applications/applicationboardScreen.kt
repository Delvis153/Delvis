package com.example.delvis.ui.theme.screens.applications

//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.navigation.compose.rememberNavController
//import androidx.navigation.NavHostController
//
//
//@Composable
//fun ApplicationBoard(navController: NavHostController) {
//    val categories = listOf("Favorites", "Applied", "Interviews", "Offers", "Rejected", "Hired") // You can expand this list
//    val applicationsMap = mapOf(
//        "Favorites" to listOf("App 1", "App 2"),
//        "Applied" to listOf("App 3"),
//        "Interviews" to listOf("Interview 1"),
//        "Offers" to listOf("Offer 1", "Offer 2"),
//        "Rejected" to listOf("Reject 1"),
//        "Hired" to listOf("Hired 1", "Hired 2"),
//    )
//
//    LazyRow (
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        items(categories) { category ->
//            Column(
//                modifier = Modifier
//                    .padding(end = 16.dp)
//                    .width(250.dp)
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        text = category,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 18.sp
//                    )
//                    IconButton(onClick = { /* Add action */ }) {
//                        Icon(
//                            imageVector = Icons.Default.Add,
//                            contentDescription = "Add"
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier.height(8.dp))
//                applicationsMap[category]?.forEach { appName ->
//                    ApplicationCard(appName)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ApplicationCard(name: String) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(150.dp)
//            .padding(vertical = 8.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        Box(modifier = Modifier.fillMaxSize()) {
//            Text(
//                text = name,
//                modifier = Modifier.align(Alignment.Center),
//                fontSize = 16.sp
//            )
//        }
//    }
//}
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ApplicationBoardPreview() {
//    ApplicationBoard(rememberNavController())
//}



import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import com.example.delvis.R
import com.example.delvis.navigation.ROUTE_APPLICATION_BOARD
import com.example.delvis.navigation.ROUTE_DASHBOARD
import com.example.delvis.navigation.ROUTE_LOGIN
import com.example.delvis.navigation.ROUTE_SEARCH_JOB

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationBoard(navController: NavHostController) {
    val categories = listOf("Favorites", "Applied", "Interviews", "Offers", "Rejected", "Hired")
    val applicationsMap = mapOf(
        "Favorites" to listOf("App 1", "App 2"),
        "Applied" to listOf("App 3"),
        "Interviews" to listOf("Interview 1"),
        "Offers" to listOf("Offer 1", "Offer 2"),
        "Rejected" to listOf("Reject 1"),
        "Hired" to listOf("Hired 1", "Hired 2")
    )
    val selectedItem = remember { mutableStateOf(0) }
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.Black) {
                NavigationBarItem(
                    selected = selectedItem.value == 0,
                    onClick = {
                        selectedItem.value = 0
                        navController.navigate(ROUTE_DASHBOARD)
                    },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Dashboard") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedItem.value == 1,
                    onClick = {
                        selectedItem.value = 1
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:thevissbeautybar@gmail.com")
                        }
                        context.startActivity(intent)
                    },
                    icon = { Icon(Icons.Filled.DateRange, contentDescription = "Email") },
                    label = { Text("Resumes") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedItem.value == 2,
                    onClick = {
                        selectedItem.value = 2
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Download app here: https://www.download.com")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    },
                    icon = { Icon(Icons.Filled.MailOutline, contentDescription = "Share") },
                    label = { Text("Letters") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedItem.value == 3,
                    onClick = {
                        selectedItem.value = 3
                        navController.navigate(ROUTE_SEARCH_JOB)
                    },
                    icon = { Icon(Icons.Filled.Build, contentDescription = "Jobs") },
                    label = { Text("Jobs") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedItem.value == 4,
                    onClick = {
                        selectedItem.value = 4
                        navController.navigate(ROUTE_APPLICATION_BOARD)
                    },
                    icon = { Icon(Icons.Filled.Create, contentDescription = "Applications") },
                    label = { Text("Applications") },
                    alwaysShowLabel = true
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Dashboard background image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.padding(innerPadding)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopAppBar(
                    title = { Text("JobSeeker") },
                    actions = {
                        IconButton(onClick = {
                            // TODO: Implement search
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }

                        IconButton(onClick = {
                            navController.navigate(ROUTE_LOGIN)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "Logout",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))


                // Categories + Application Cards
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    items(categories) { category ->
                        Column(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .width(250.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = category,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                                IconButton(onClick = { /* Add action */ }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add",
                                        tint = Color.White
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            applicationsMap[category]?.forEach { appName ->
                                ApplicationCard(appName)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ApplicationCard(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = name,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ApplicationBoardPreview() {
    ApplicationBoard(rememberNavController())
}





