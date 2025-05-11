package com.example.delvis.ui.theme.screens.requirements

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.border
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.delvis.R
import com.example.delvis.navigation.ROUTE_DASHBOARD
import com.example.delvis.navigation.ROUTE_LOGIN
import com.example.delvis.navigation.ROUTE_RESUME
import com.example.delvis.navigation.ROUTE_VIEW_APPLICATION
import com.example.delvis.navigation.ROUTE_VIEW_JOBS
import com.example.delvis.ui.theme.screens.dashboard.shareApp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Work


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumeScreen(navController: NavHostController) {
    val selectedItem = remember { mutableStateOf(1) }
    val context = LocalContext.current

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = selectedItem.value == 0,
                    onClick = {
                        selectedItem.value = 0
                        navController.navigate(ROUTE_DASHBOARD)
                    },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home", fontSize = 10.sp, maxLines = 1, softWrap = false) },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedItem.value == 1,
                    onClick = {
                        selectedItem.value = 1
                        navController.navigate(ROUTE_RESUME)
                    },
                    icon = { Icon(Icons.Filled.Article, contentDescription = "Resume") },
                    label = { Text("Resume") },
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
                    icon = { Icon(Icons.Filled.Send, contentDescription = "Share") },
                    label = { Text("Share") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedItem.value == 3,
                    onClick = {
                        selectedItem.value = 3
                        navController.navigate(ROUTE_VIEW_JOBS)
                    },
                    icon = { Icon(Icons.Filled.Work, contentDescription = "Jobs") },
                    label = { Text("Jobs") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedItem.value == 4,
                    onClick = {
                        selectedItem.value = 4
                        navController.navigate(ROUTE_VIEW_APPLICATION)
                    },
                    icon = { Icon(Icons.Filled.List, contentDescription = "Applications") },
                    label = {
                        Text("Applications", fontSize = 10.sp, maxLines = 1, softWrap = false)
                    },
                    alwaysShowLabel = true
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Top bar
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .height(32.dp)
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = "JobSeeker",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                },
                actions = {
                  IconButton(onClick = { /* TODO: Implement search */ }) {
                       Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.Black)
                    }
                    IconButton(onClick = { navController.navigate(ROUTE_LOGIN) }) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Logout", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Section cards
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    SectionCard(
                        title = "Resumes",
                        onCreateClick = { navController.navigate("create_resume") },
                        onArrowClick = { navController.navigate("resumes_list") }
                    )
                }
                item {
                    SectionCard(
                        title = "Cover Letters",
                        onCreateClick = { navController.navigate("create_cover_letter") },
                        onArrowClick = { navController.navigate("cover_letters_list") }
                    )
                }
            }

            // Floating Action Button
            Box(modifier = Modifier.fillMaxSize()) {
                FloatingActionButton(
                    onClick = { navController.navigate("create_document") },
                    containerColor = Color(0xFF6200EE),
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    onCreateClick: () -> Unit,
    onArrowClick: () -> Unit
) {
    val purple = Color(0xFF6200EE)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = purple)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                IconButton(onClick = onArrowClick) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "View All",
                        tint = Color.White
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clickable(onClick = onCreateClick)
                    .background(purple, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Create New", color = Color.White)
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResumePreview() {
    ResumeScreen(rememberNavController())
}


