package com.example.delvis.ui.theme.screens.applications

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.delvis.R
import coil.compose.AsyncImage
import com.example.delvis.data.ApplicationViewModel
import com.example.delvis.navigation.ROUTE_DASHBOARD
import androidx.compose.ui.text.TextStyle



@Composable
fun AddApplicationScreen(navController: NavController) {
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
    val context = LocalContext.current

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
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        )

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

        Text(
            text = "Attach your CV's photo",
            color = Color.LightGray,
            modifier = Modifier.padding(bottom = 12.dp)
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
            label = { Text("Applicants Gender", color = Color.White) },
            placeholder = { Text("Please enter your gender", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White)
        )

        OutlinedTextField(
            value = applicantsDesiredjob,
            onValueChange = { applicantsDesiredjob = it },
            label = { Text("Applicants Desired Job ", color = Color.White) },
            placeholder = { Text("Please enter the desired job", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White)
        )

        OutlinedTextField(
            value = applicantsExperience,
            onValueChange = { applicantsExperience= it },
            label = { Text("Applicants Experience", color = Color.White) },
            placeholder = { Text("Please enter your job experience", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White)
        )

        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Brief description", color = Color.White) },
            placeholder = { Text("Please enter application's description", color = Color.Gray) },
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
                    imageUri.value?.let {
                        applicationViewModel.uploadApplicationWithImage(
                            it,
                            context,
                            applicantsName,
                            applicantsGender,
                            applicantsExperience,
                            applicantsDesiredjob,
                            desc,
                            navController
                        )
                    } ?: Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
            ) {
                Text("Save", color = Color.Black)
            }
        }
    }
}




