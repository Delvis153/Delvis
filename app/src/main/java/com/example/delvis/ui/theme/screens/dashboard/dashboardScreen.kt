package com.example.delvis.ui.theme.screens.dashboard

import android.R.attr.title
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.delvis.R
import com.example.delvis.navigation.ROUTE_ADD_APPLICATION
import com.example.delvis.navigation.ROUTE_ADD_JOB
import com.example.delvis.navigation.ROUTE_ADD_PRODUCT
import com.example.delvis.navigation.ROUTE_APPLICATION_BOARD
import com.example.delvis.navigation.ROUTE_DASHBOARD
import com.example.delvis.navigation.ROUTE_LOGIN
import com.example.delvis.navigation.ROUTE_SEARCH_JOB
import com.example.delvis.navigation.ROUTE_VIEW_JOBS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    val selectedItem = remember { mutableStateOf(0) }
    val context = LocalContext.current
    Scaffold(
//        bottomBar = {
//            NavigationBar(containerColor = Color.Black) {
//                NavigationBarItem(
//                    selected = selectedItem.value == 0,
//                    onClick = {
//                        selectedItem.value = 0
//                        val intent = Intent(Intent.ACTION_DIAL).apply {
//                            data = Uri.parse("tel:0722949328")
//                        }
//                        context.startActivity(intent)
//                    },
//                    icon = { Icon(Icons.Filled.Phone, contentDescription = "Phone") },
//                    label = { Text("Phone") },
//                    alwaysShowLabel = true
//                )
//                NavigationBarItem(
//                    selected = selectedItem.value == 1,
//                    onClick = {
//                        selectedItem.value = 1
//                        val intent = Intent(Intent.ACTION_SENDTO).apply {
//                            data = Uri.parse("mailto:thevissbeautybar@gmail.com")
//                            putExtra(Intent.EXTRA_SUBJECT, "Make an Order")
//                            putExtra(Intent.EXTRA_TEXT, "Give Me The Transaction Process Breakdown")
//                        }
//                        context.startActivity(intent)
//                    },
//                    icon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
//                    label = { Text("Email") },
//                    alwaysShowLabel = true
//                )
//                NavigationBarItem(
//                    selected = selectedItem.value == 2,
//                    onClick = {
//                        selectedItem.value = 2
//                        val sendintent = Intent().apply {
//                            action = Intent.ACTION_SEND
//                            putExtra(
//                                Intent.EXTRA_TEXT,
//                                "Download app here: https://www.download.com"
//                            )
//                            type = "text/plain"
//                        }
//                        val shareIntent = Intent.createChooser(sendintent, null)
//                        context.startActivity(shareIntent)
//                    },
//                    icon = { Icon(Icons.Filled.Share, contentDescription = "Share") },
//                    label = { Text("Share") },
//                    alwaysShowLabel = true
//                )
//            }
//        }
        bottomBar = {
            NavigationBar(containerColor = Color.Black){
                NavigationBarItem(
                    selected = selectedItem.value == 0,
                    onClick = {
                        selectedItem.value = 0
                        navController.navigate(ROUTE_DASHBOARD)
//                        val intent = Intent(Intent.ACTION_DIAL).apply{
//                            data= Uri.parse("tel:0722949328")}
//                        context.startActivity(intent)
                    },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Dashboard") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedItem.value == 1,
                    onClick = {
                        selectedItem.value = 1
                        val intent = Intent(Intent.ACTION_SENDTO).apply{
                            data= Uri.parse("mailto:thevissbeautybar@gmail.com")
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
                        val sendintent = Intent().apply{
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Download app here: https://www.download.com")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendintent, null)
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
    ) {
        innerPadding ->
        Box {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Dashboard background image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.padding(innerPadding)
            )

        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = {
                    Text(text = "JobSeeker")
                },
//                navigationIcon = {
//                    IconButton(onClick = {}) {
//                        Icon(
//                            imageVector = Icons.Filled.Home,
//                            contentDescription = "Home"
//                        )
//                    }
//                },
                actions = {
//                    IconButton(onClick = {}) {
//                        Icon(
//                            imageVector = Icons.Filled.Person,
//                            contentDescription = "Profile"
//                        )
//                    }
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate(ROUTE_LOGIN)
                        }) {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "Logout"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Magenta,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )

            )
            Row(modifier = Modifier.wrapContentSize()) {
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {navController.navigate(ROUTE_ADD_APPLICATION)},
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(10.dp),
                    colors = CardDefaults.cardColors(Color.Cyan)

                ) {
                    Box(
                        modifier = Modifier.height(100.dp).padding(25.dp),
                        contentAlignment = Alignment.Center
                    ) { Text(text = "Make An Application") }
                }


//                Card(
//                    modifier = Modifier.padding(10.dp),
//                    shape = RoundedCornerShape(20.dp),
//                    elevation = CardDefaults.cardElevation(10.dp),
//                    colors = CardDefaults.cardColors(Color.Cyan)
//
//                ) {
//                    Box(
//                        modifier = Modifier.height(100.dp).padding(25.dp),
//                        contentAlignment = Alignment.Center
//                    ) { Text(text = "Orders") }
//                }

//                Card(
//                    modifier = Modifier.padding(10.dp),
//                    shape = RoundedCornerShape(20.dp),
//                    elevation = CardDefaults.cardElevation(10.dp),
//                    colors = CardDefaults.cardColors(Color.Cyan)
//
//                ) {
//                    Box(
//                        modifier = Modifier.height(100.dp).padding(25.dp),
//                        contentAlignment = Alignment.Center
//                    ) { Text(text = "Payments") }
//                }
            }


            Row(modifier = Modifier.wrapContentSize()) {
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {navController.navigate(ROUTE_ADD_JOB)},

                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(10.dp),
                    colors = CardDefaults.cardColors(Color.Cyan)

                ) {
                    Box(
                        modifier = Modifier.height(100.dp).padding(25.dp),
                        contentAlignment = Alignment.Center
                    ) { Text(text = "Add a Job") }
                }

//                Card(
//                    modifier = Modifier.padding(10.dp),
//                    shape = RoundedCornerShape(20.dp),
//                    elevation = CardDefaults.cardElevation(10.dp),
//                    colors = CardDefaults.cardColors(Color.Cyan)
//
//                ) {
//                    Box(
//                        modifier = Modifier.height(100.dp).padding(25.dp),
//                        contentAlignment = Alignment.Center
//                    ) { Text(text = "Sales") }
//                }
//
//                Card(
//                    modifier = Modifier.padding(10.dp),
//                    shape = RoundedCornerShape(20.dp),
//                    elevation = CardDefaults.cardElevation(10.dp),
//                    colors = CardDefaults.cardColors(Color.Cyan)
//
//                ) {
//                    Box(
//                        modifier = Modifier.height(100.dp).padding(25.dp),
//                        contentAlignment = Alignment.Center
//                    ) { Text(text = "Employees") }
//                }
            }
//            Row(modifier = Modifier.wrapContentSize()) {
//                Card(
//                    modifier = Modifier
//                        .padding(10.dp)
//                        .clickable {navController.navigate(ROUTE_ADD_PRODUCT)},
//                    shape = RoundedCornerShape(20.dp),
//                    elevation = CardDefaults.cardElevation(10.dp),
//                    colors = CardDefaults.cardColors(Color.Cyan)
//
//                ) {
//                    Box(
//                        modifier = Modifier.height(100.dp).padding(25.dp),
//                        contentAlignment = Alignment.Center
//                    ) { Text(text = "Cover Letters") }
//                }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(rememberNavController())
}
