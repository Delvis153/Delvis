package com.example.delvis.ui.theme.screens.jobs


import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.delvis.data.JobViewModel
import com.example.delvis.navigation.ROUTE_DASHBOARD


@Composable
fun AddjobScreen(navController: NavController) {
    val imageUri = rememberSaveable { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { imageUri.value = it }
    }

    var jobpositionname by remember { mutableStateOf("") }
    var joblocation by remember { mutableStateOf("") }
    var jobclosingdateforapplication by remember { mutableStateOf("") }
    var jobflexibility by remember { mutableStateOf("") }

    val jobViewModel: JobViewModel = viewModel()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ADD A JOB",
            fontSize = 24.sp,
            color = Color.Cyan,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = jobpositionname,
            onValueChange = { jobpositionname = it },
            label = { Text("Job Position Name", color = Color.White) },
            placeholder = { Text("Please enter your Job's Position Name", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
        )

        OutlinedTextField(
            value = joblocation,
            onValueChange = { joblocation = it },
            label = { Text("Job Location", color = Color.White) },
            placeholder = { Text("Please enter your Job's location", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
        )

        OutlinedTextField(
            value = jobclosingdateforapplication,
            onValueChange = { jobclosingdateforapplication = it },
            label = { Text("Job closing date for Application", color = Color.White) },
            placeholder = { Text("Please enter your Job's closing date for application", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
        )

        OutlinedTextField(
            value = jobflexibility,
            onValueChange = { jobflexibility = it },
            label = { Text("Job Flexibility", color = Color.White) },
            placeholder = { Text("Please enter your Job's Flexibility", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate(ROUTE_DASHBOARD)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text(text = "Home", color = Color.White)
            }

            Button(
                onClick = {
                    jobViewModel.saveJob(
                        jobpositionname,
                        joblocation,
                        jobclosingdateforapplication,
                        jobflexibility,
                        navController
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
            ) {
                Text(text = "Save", color = Color.Black)
            }

        }
    }
}


