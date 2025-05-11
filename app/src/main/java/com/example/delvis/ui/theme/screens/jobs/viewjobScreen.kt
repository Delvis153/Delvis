package com.example.delvis.ui.theme.screens.jobs

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.delvis.R
import com.example.delvis.data.JobViewModel
import com.example.delvis.models.JobModel
import com.example.delvis.navigation.ROUTE_DASHBOARD
import com.example.delvis.navigation.ROUTE_LOGIN
import com.example.delvis.navigation.ROUTE_RESUME
import com.example.delvis.navigation.ROUTE_VIEW_APPLICATION
import com.example.delvis.navigation.ROUTE_VIEW_JOBS
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewJobs(navController: NavHostController) {
    val context = LocalContext.current
    val jobRepository = JobViewModel()
    val emptyJobState = remember { mutableStateOf(JobModel("", "", "", "", "", "")) }
    val jobListState = remember { mutableStateListOf<JobModel>() }
    val selectedItem = remember { mutableStateOf(3) }

    LaunchedEffect(Unit) {
        jobRepository.viewJobs(emptyJobState, jobListState, context)
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                        IconButton(onClick = { /* TODO: Implement search */ }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = Color.Black
                            )
                        }
                        IconButton(onClick = {
                            navController.navigate(ROUTE_LOGIN)
                        }) {
                            Icon(
                                Icons.Filled.AccountCircle,
                                contentDescription = "Logout",
                                tint = Color.Black
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )

                Text(
                    text = "All Jobs",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                ) {
                    items(jobListState) { job ->
                        AnimatedJobItem(
                            job = job,
                            navController = navController,
                            jobRepository = jobRepository
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedJobItem(
    job: JobModel,
    navController: NavHostController,
    jobRepository: JobViewModel
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
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF6200EE),
        tonalElevation = 4.dp,
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(100.dp),
                    color = Color.White
                ) {
                    AsyncImage(
                        model = job.imageUrl,
                        contentDescription = "Job Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = job.jobpositionname,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = job.joblocation,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.LightGray
                    )
                }

                Row {
                    IconButton(onClick = {
                        navController.navigate("update_job/${job.jobId}")
                    }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = Color.White)
                    }
                    IconButton(onClick = {
                        jobRepository.deleteJob(context, job.jobId, navController)
                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.White)
                    }
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                Text("Closing Date: ${job.jobclosingdateforapplication}", color = Color.White)
                Text("Flexibility: ${job.jobflexibility}", color = Color.White)
                Text(
                    text = "Job ID: ${job.jobId}",
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tap to view more...",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewJobsPreview() {
    ViewJobs(rememberNavController())
}

