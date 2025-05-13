package com.example.delvis.ui.theme.screens.requirements


// Jetpack Compose
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delvis.data.ResumePreviewViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.res.painterResource
import com.example.delvis.R
import com.example.delvis.navigation.ROUTE_DASHBOARD
import com.example.delvis.navigation.ROUTE_LOGIN
import com.example.delvis.navigation.ROUTE_RESUME
import com.example.delvis.navigation.ROUTE_VIEW_APPLICATION
import com.example.delvis.navigation.ROUTE_VIEW_JOBS
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.delvis.navigation.ROUTE_RESUME_PREVIEW

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumePreviewScreen(navController: NavController) {
    val viewModel: ResumePreviewViewModel = viewModel()
    val resume = viewModel.resume.value // Access the resume data
    val selectedItem = remember { mutableStateOf(2) }

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
                    icon = { Icon(Icons.Filled.AllInbox, contentDescription = "Docs") },
                    label = { Text("Docs") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedItem.value == 2,
                    onClick = {
                        selectedItem.value = 2
                        navController.navigate(ROUTE_RESUME_PREVIEW)
                    },
                    icon = { Icon(Icons.Filled.Article, contentDescription = "Resume") },
                    label = { Text("Resume") },
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
                .padding(16.dp)
                .background(Color.White),
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
                        Text(
                            text = "JobSeeker",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                },
                actions = {
//                    IconButton(onClick = { /* TODO: Implement search */ }) {
//                        Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.Black)
//                    }
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
                text = "FORMAT FOR YOUR RESUME",
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Display resume details here
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header: Name, Title, Contact Info
                ResumeHeader(
                    name = resume.name,
                    title = resume.title,
                    email = resume.email,
                    phone = resume.phone,
                    linkedIn = resume.linkedIn
                )

                Divider()

                // Professional Summary
                ResumeSection(title = "Summary") {
                    Text(resume.summary)
                }

                Divider()

                // Education Section
                if (resume.education.isNotEmpty()) {
                    ResumeSection(title = "Education") {
                        resume.education.forEach { education ->
                            ResumeEducation(
                                institution = education.institution,
                                degree = education.degree,
                                year = education.graduationYear
                            )
                        }
                    }
                }

                Divider()

                // Experience Section
                if (resume.experience.isNotEmpty()) {
                    ResumeSection(title = "Experience") {
                        resume.experience.forEach { job ->
                            ResumeJob(
                                company = job.company,
                                role = job.role,
                                period = job.period,
                                description = job.description
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }

                Divider()

                // Skills Section
                if (resume.skills.isNotEmpty()) {
                    ResumeSection(title = "Skills") {
                        Text(resume.skills.joinToString(", "))
                    }
                }
            }
        }
    }
}

// Header Composable for Name, Title, Email, etc.
@Composable
fun ResumeHeader(
    name: String,
    title: String,
    email: String,
    phone: String,
    linkedIn: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text(title, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Spacer(Modifier.height(8.dp))
        Text(email, fontSize = 14.sp)
        Text(phone, fontSize = 14.sp)
        Text(linkedIn, fontSize = 14.sp)
    }
}

// Section composable for each major section (Education, Experience, Skills, etc.)
@Composable
fun ResumeSection(title: String, content: @Composable () -> Unit) {
    Column {
        Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(4.dp))
        content()
    }
}

// Education Item Composable
@Composable
fun ResumeEducation(institution: String, degree: String, year: String) {
    Column {
        Text("$degree at $institution", fontWeight = FontWeight.Bold)
        Text("Graduation Year: $year", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}

// Job Item Composable (Experience)
@Composable
fun ResumeJob(company: String, role: String, period: String, description: String) {
    Column {
        Text("$role at $company", fontWeight = FontWeight.Bold)
        Text(period, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        Text(description)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResumePreviewViewScreen() {
    ResumePreviewScreen(rememberNavController())
}
