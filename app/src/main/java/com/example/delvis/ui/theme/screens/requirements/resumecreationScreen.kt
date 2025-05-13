package com.example.delvis.ui.theme.screens.requirements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.delvis.data.ResumeCreationViewModel
// Jetpack Compose
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import com.example.delvis.models.Education
import com.example.delvis.models.Experience


@Composable
fun ResumecreationScreen(navController: NavController) {
    val viewModel: ResumeCreationViewModel = viewModel()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Create Your Resume", style = MaterialTheme.typography.headlineSmall)

        // Basic Info
        TextField(
            value = viewModel.resume.name,
            onValueChange = viewModel::updateName,
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = viewModel.resume.title,
            onValueChange = viewModel::updateTitle,
            label = { Text("Job Title") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = viewModel.resume.email,
            onValueChange = viewModel::updateEmail,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = viewModel.resume.phone,
            onValueChange = viewModel::updatePhone,
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = viewModel.resume.linkedIn,
            onValueChange = viewModel::updateLinkedIn,
            label = { Text("LinkedIn Profile") },
            modifier = Modifier.fillMaxWidth()
        )

        // Summary
        TextField(
            value = viewModel.resume.summary,
            onValueChange = viewModel::updateSummary,
            label = { Text("Professional Summary") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            maxLines = 4
        )

        // Education Entry (single field for simplicity)
        var eduInstitution by remember { mutableStateOf("") }
        var eduDegree by remember { mutableStateOf("") }
        var eduYear by remember { mutableStateOf("") }

        Text("Add Education", fontWeight = FontWeight.Bold)
        TextField(
            value = eduInstitution,
            onValueChange = { eduInstitution = it },
            label = { Text("Institution") }
        )
        TextField(
            value = eduDegree,
            onValueChange = { eduDegree = it },
            label = { Text("Degree") }
        )
        TextField(
            value = eduYear,
            onValueChange = { eduYear = it },
            label = { Text("Graduation Year") }
        )
        Button(onClick = {
            if (eduInstitution.isNotBlank() && eduDegree.isNotBlank()) {
                viewModel.addEducation(
                    Education(eduInstitution, eduDegree, eduYear)
                )
                eduInstitution = ""
                eduDegree = ""
                eduYear = ""
            }
        }) {
            Text("Add Education")
        }

        // Experience Entry
        var expCompany by remember { mutableStateOf("") }
        var expRole by remember { mutableStateOf("") }
        var expPeriod by remember { mutableStateOf("") }
        var expDescription by remember { mutableStateOf("") }

        Text("Add Experience", fontWeight = FontWeight.Bold)
        TextField(value = expCompany, onValueChange = { expCompany = it }, label = { Text("Company") })
        TextField(value = expRole, onValueChange = { expRole = it }, label = { Text("Role") })
        TextField(value = expPeriod, onValueChange = { expPeriod = it }, label = { Text("Period") })
        TextField(
            value = expDescription,
            onValueChange = { expDescription = it },
            label = { Text("Description") },
            maxLines = 3
        )

        Button(onClick = {
            if (expCompany.isNotBlank() && expRole.isNotBlank()) {
                viewModel.addExperience(
                    Experience(expCompany, expRole, expPeriod, expDescription)
                )
                expCompany = ""
                expRole = ""
                expPeriod = ""
                expDescription = ""
            }
        }) {
            Text("Add Experience")
        }

        // Skills Entry
        var newSkill by remember { mutableStateOf("") }

        Text("Add Skill", fontWeight = FontWeight.Bold)
        TextField(
            value = newSkill,
            onValueChange = { newSkill = it },
            label = { Text("Skill") }
        )
        Button(onClick = {
            if (newSkill.isNotBlank()) {
                viewModel.addSkill(newSkill)
                newSkill = ""
            }
        }) {
            Text("Add Skill")
        }

        // Submit / Save
        Button(
            onClick = {
                viewModel.saveToFirebase(
                    userId = viewModel.resume.email.replace(".", "_"), // Firebase-safe key
                    onSuccess = { println("Saved!") },
                    onError = { e -> println("Failed to save: ${e.message}") }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Resume")
        }

    }
}

