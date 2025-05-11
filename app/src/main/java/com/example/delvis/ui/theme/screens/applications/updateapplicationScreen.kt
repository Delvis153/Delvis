package com.example.delvis.ui.theme.screens.applications

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.delvis.data.ApplicationViewModel
import com.example.delvis.R
import com.example.delvis.models.ApplicationModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel



@Composable
fun UpdateApplicationScreen(navController: NavController, applicationId: String) {
    val context = LocalContext.current
    val imageUri = rememberSaveable { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { imageUri.value = it }
    }

    var applicantsName by remember { mutableStateOf("") }
    var applicantsDesiredjob by remember { mutableStateOf("") }
    var applicantsExperience by remember { mutableStateOf("") }
    var applicantsGender by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    val applicationViewModel: ApplicationViewModel = viewModel()

    val currentDataRef = FirebaseDatabase.getInstance()
        .getReference("Applications")
        .child(applicationId)

    // Fetch existing application data
    DisposableEffect(Unit) {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val application = snapshot.getValue(ApplicationModel::class.java)
                application?.let {
                    applicantsName = it.applicantsName
                    applicantsGender = it.applicantsGender
                    applicantsDesiredjob = it.applicantsDesiredjob
                    applicantsExperience = it.applicantsExperience
                    desc = it.desc
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
            }
        }

        currentDataRef.addValueEventListener(listener)
        onDispose { currentDataRef.removeEventListener(listener) }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .padding(10.dp)
                .size(200.dp)
        ) {
            AsyncImage(
                model = imageUri.value ?: R.drawable.ic_person,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clickable { launcher.launch("image/*") }
            )
        }

        Text(text = "Attach application image")

        OutlinedTextField(
            value = applicantsName,
            onValueChange = { applicantsName = it },
            label = { Text("Applicants Name") },
            placeholder = { Text("Please enter your name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = applicantsGender,
            onValueChange = { applicantsGender = it },
            label = { Text("Applicants Gender") },
            placeholder = { Text("Please enter your gender") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = applicantsDesiredjob,
            onValueChange = { applicantsDesiredjob = it },
            label = { Text("Applicants Desired Job") },
            placeholder = { Text("Please enter your desired job") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = applicantsExperience,
            onValueChange = { applicantsExperience = it },
            label = { Text("Unit Applicants Experience") },
            placeholder = { Text("Please enter your jobs experience") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Brief description") },
            placeholder = { Text("Please enter application description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            singleLine = false
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate("AllApplicationsScreen") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(text = "All Applications")
            }

            Button(
                onClick = {
                    applicationViewModel.updateApplication(
                        context = context,
                        navController = navController,
                        applicantsName = applicantsName,
                        applicantsGender = applicantsGender,
                        applicantsDesiredjob = applicantsDesiredjob,
                        applicantsExperience = applicantsExperience,
                        desc = desc,
                        applicationId = applicationId,
                        imageUri = imageUri.value // Pass image if needed
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
            ) {
                Text(text = "UPDATE")
            }
        }
    }
}
