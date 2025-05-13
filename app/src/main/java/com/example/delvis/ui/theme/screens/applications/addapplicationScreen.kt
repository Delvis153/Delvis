package com.example.delvis.ui.theme.screens.applications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.delvis.data.ApplicationViewModel
import com.example.delvis.navigation.ROUTE_DASHBOARD
import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.*
import com.example.delvis.navigation.ROUTE_SUBSCRIPTION


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDropdown(
    selectedJob: String,
    onJobSelected: (String) -> Unit,
    jobList: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedJob,
            onValueChange = {},
            readOnly = true,
            label = { Text("Applicant's Desired Job", color = Color.White) },
            placeholder = { Text("Select desired job", color = Color.Gray) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(), // <-- THIS FIXES THE DROPDOWN ISSUE
            textStyle = TextStyle(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White
            )
        )


        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            jobList.forEach { job ->
                DropdownMenuItem(
                    text = { Text(job) },
                    onClick = {
                        onJobSelected(job)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun AddApplicationScreen(navController: NavController) {
    var applicantsName by remember { mutableStateOf("") }
    var applicantsDesiredjob by remember { mutableStateOf("") }
    var applicantsExperience by remember { mutableStateOf("") }
    var applicantsGender by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    val applicationViewModel: ApplicationViewModel = viewModel()
    val context = LocalContext.current
    val jobList = applicationViewModel.jobList



//    // Function to simulate fetching the content from the CreateResumeScreen
//    fun fetchResumeContent() {
//        // Simulate fetching the data for resume (e.g., from a ViewModel or database)
//        applicantsName = applicantsName,
//        applicantsDesiredjob = "Software Engineer" // Example value
//        applicantsExperience = "5 years of experience in mobile development" // Example value
//        applicantsGender = "Male" // Example value
//        desc = "Passionate developer with experience in Kotlin and Jetpack Compose" // Example value
//    }
//
//    // Call to simulate fetching content when the screen starts or on certain action
//    LaunchedEffect(Unit) {
//        fetchResumeContent()
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ADD NEW APPLICATION",
            fontSize = 24.sp,
            color = Color.Cyan,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = applicantsName,
            onValueChange = { applicantsName = it },
            label = { Text("Applicant's Name", color = Color.White) },
            placeholder = { Text("Please enter your name", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White)
        )

        OutlinedTextField(
            value = applicantsGender,
            onValueChange = { applicantsGender = it },
            label = { Text("Applicant's Gender", color = Color.White) },
            placeholder = { Text("Please enter your gender", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White)
        )

        OutlinedTextField(
            value = applicantsDesiredjob,
            onValueChange = { applicantsDesiredjob = it },
            label = { Text("Applicant's Desired job", color = Color.White) },
            placeholder = { Text("Please enter your Desired Job", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White)
        )

        OutlinedTextField(
            value = applicantsExperience,
            onValueChange = { applicantsExperience = it },
            label = { Text("Applicant's Experience", color = Color.White) },
            placeholder = { Text("Please enter your experience", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White)
        )

        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Brief Description", color = Color.White) },
            placeholder = { Text("Please enter description", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            singleLine = false,
            textStyle = TextStyle(color = Color.White)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate(ROUTE_DASHBOARD) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text("Home", color = Color.White)
            }

            Button(
                onClick = {
//                    navController.navigate(ROUTE_SUBSCRIPTION)
                    applicationViewModel.saveApplication(
                        context,
                        applicantsName,
                        applicantsGender,
                        applicantsExperience,
                        applicantsDesiredjob,
                        desc,
                        navController
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
            ) {
                Text("Save", color = Color.Black)
            }
        }
    }
}





