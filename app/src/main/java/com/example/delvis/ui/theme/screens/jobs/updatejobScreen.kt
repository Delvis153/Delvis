package com.example.delvis.ui.theme.screens.jobs

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
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
import com.example.delvis.data.JobViewModel
import com.example.delvis.R
import com.example.delvis.models.JobModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun UpdatejobScreen(navController: NavController, jobId: String) {
    val context = LocalContext.current
    val imageUri = rememberSaveable { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { imageUri.value = it }
    }

    var jobPositionName by remember { mutableStateOf("") }
    var jobLocation by remember { mutableStateOf("") }
    var jobClosingDate by remember { mutableStateOf("") }
    var jobFlexibility by remember { mutableStateOf("") }

    val currentDataRef = FirebaseDatabase.getInstance()
        .getReference("Jobs")
        .child(jobId)

    // Fetch job data once and populate UI
    DisposableEffect(Unit) {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val job = snapshot.getValue(JobModel::class.java)
                job?.let {
                    jobPositionName = it.jobpositionname
                    jobLocation = it.joblocation
                    jobClosingDate = it.jobclosingdateforapplication
                    jobFlexibility = it.jobflexibility
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
            }
        }

        currentDataRef.addValueEventListener(listener)
        onDispose {
            currentDataRef.removeEventListener(listener)
        }
    }

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

        Text(text = "Attach job image")

        OutlinedTextField(
            value = jobPositionName,
            onValueChange = { jobPositionName = it },
            label = { Text("Job Name") },
            placeholder = { Text("Please enter job position name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = jobLocation,
            onValueChange = { jobLocation = it },
            label = { Text("Job Location") },
            placeholder = { Text("Please enter job location") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = jobClosingDate,
            onValueChange = { jobClosingDate = it },
            label = { Text("Closing Date") },
            placeholder = { Text("Please enter closing date") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = jobFlexibility,
            onValueChange = { jobFlexibility = it },
            label = { Text("Job Flexibility") },
            placeholder = { Text("Please enter job flexibility") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            singleLine = false
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                navController.navigate("view_jobs")
            }) {
                Text("All Jobs")
            }

            Button(onClick = {
                val jobRepository = JobViewModel() // ideally JobViewModel
                jobRepository.updateJob(
                    context = context,
                    navController = navController,
                    jobpositionname = jobPositionName,
                    joblocation = jobLocation,
                    jobclosingdateforapplication = jobClosingDate,
                    jobflexibility = jobFlexibility,
                    jobId = jobId
                )
            }) {
                Text("UPDATE")
            }
        }
    }
}
