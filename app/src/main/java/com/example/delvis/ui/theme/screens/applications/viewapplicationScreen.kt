package com.example.delvis.ui.theme.screens.applications

import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.delvis.R
import com.example.delvis.data.ApplicationViewModel
import com.example.delvis.models.ApplicationModel
import com.example.delvis.navigation.ROUTE_DASHBOARD
import com.example.delvis.navigation.ROUTE_LOGIN
import com.example.delvis.navigation.ROUTE_RESUME
import com.example.delvis.navigation.ROUTE_VIEW_APPLICATION
import com.example.delvis.navigation.ROUTE_VIEW_JOBS
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Work
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewApplications(navController: NavHostController) {
    val context = LocalContext.current
    val applicationRepository = remember { ApplicationViewModel() }
    val emptyApplicationState = remember { mutableStateOf(ApplicationModel("", "", "", "", "", "")) }
    val applicationListState = remember { mutableStateListOf<ApplicationModel>() }
    val selectedItem = remember { mutableStateOf(4) }

    LaunchedEffect(Unit) {
        applicationRepository.viewApplications(
            emptyApplicationState,
            applicationListState,
            context
        )
    }

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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                        Text(text = "JobSeeker")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Search */ }) {
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

            Text(
                text = "Job Applications",
                fontSize = 28.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (applicationListState.isEmpty()) {
                Text(
                    text = "No applications submitted yet.",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(applicationListState) { application ->
                        AnimatedApplicationItem(
                            application = application,
                            navController = navController,
                            applicationRepository = applicationRepository
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedApplicationItem(
    application: ApplicationModel,
    navController: NavHostController,
    applicationRepository: ApplicationViewModel
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFF6200EE), // Purple background
        tonalElevation = 2.dp,
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    modifier = Modifier.size(60.dp),
                    color = Color.White
                ) {
                    AsyncImage(
                        model = application.imageUrl,
                        contentDescription = "Applicant Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(60.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = application.applicantsName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = application.applicantsDesiredjob,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.LightGray
                    )
                }
                Row {
                    IconButton(onClick = {
                        navController.navigate("update_application/${application.applicationId}Id")
                    }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = Color.White)
                    }
                    IconButton(onClick = {
                        applicationRepository.deleteApplication(
                            context,
                            application.applicationId,
                            navController
                        )
                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.White)
                    }
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                Text("Gender: ${application.applicantsGender}", color = Color.White)
                Text("Experience: ${application.applicantsExperience}", color = Color.White)
                Text(
                    text = "Description:",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(application.desc, color = Color.White.copy(alpha = 0.9f))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Application ID: ${application.applicationId}",
                    color = Color.LightGray,
                    fontSize = 12.sp
                )
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = application.desc,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ViewApplicationsPreview() {
    val navController = rememberNavController()
    ViewApplications(navController = navController)
}
