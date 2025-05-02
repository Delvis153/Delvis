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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.delvis.R
import coil.compose.AsyncImage
import com.example.delvis.data.JobViewModel
import com.example.delvis.navigation.ROUTE_ADD_JOB

@Composable
fun AddjobScreen(navController: NavController){
    val imageUri = rememberSaveable() { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent())
    { uri:Uri? -> uri?.let{imageUri.value=it} }
    var jobpositionname by remember { mutableStateOf("")}
    var joblocation by remember { mutableStateOf("") }
    var jobclosingdateforapplication by remember { mutableStateOf("") }
    var jobflexibility by remember { mutableStateOf("") }
//    var jobcontractduration by remember { mutableStateOf("") }
    val JobViewModel: JobViewModel = viewModel()
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "ADD A JOB",
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.Black)
                .padding(7.dp)
                .fillMaxWidth()
        )

        Card (shape = CircleShape,
            modifier = Modifier.padding(10.dp).size(200.dp)
        ){

            AsyncImage(
                model = imageUri.value ?:R.drawable.ic_person,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp)
                    .clickable{launcher.launch("image/*")}
            )
        }
        Text(text = "Attach your  photo")
        OutlinedTextField(
            value = jobpositionname,
            onValueChange = {newJobpositionname -> jobpositionname = newJobpositionname},
            label = {Text(text = "Job Position Name") },
            placeholder = {Text(text = "Please enter your Job's Position Name ")},
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = joblocation,
            onValueChange = {newJoblocation -> joblocation = newJoblocation},
            label = {Text(text = "Job Location") },
            placeholder = {Text(text = "Please enter your Job's location")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = jobclosingdateforapplication,
            onValueChange = {newJobclosingdateforapplication -> jobclosingdateforapplication = newJobclosingdateforapplication},
            label = {Text(text = "Job closing date for Application") },
            placeholder = {Text(text = "Please enter your Job's closing date for application")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = jobflexibility,
            onValueChange ={newJobflexibility -> jobflexibility = newJobflexibility},
            label = {Text(text = "Job Flexibility")},
            placeholder = {Text(text = "Please enter your Job's Flexibility")},
            modifier = Modifier.fillMaxWidth()
        )
//        OutlinedTextField(
//            value = jobcontractduration,
//            onValueChange ={newJobcontractduration -> jobcontractduration = newJobcontractduration},
//            label = {Text(text = "Job contract duration")},
//            placeholder = {Text(text = "Please enter your Job's contract duration")},
//            modifier = Modifier.fillMaxWidth().height(150.dp),
//            singleLine = false
//        )
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                navController.navigate(ROUTE_ADD_JOB)
            }) { Text(text = "Home") }
            Button(onClick = {
                imageUri.value?.let {
                    JobViewModel.uploadProductWithImage(it, context, jobpositionname, joblocation, jobclosingdateforapplication, jobflexibility, navController)
                } ?: Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
            }, colors = ButtonDefaults.buttonColors(Color.Cyan)){ Text(text = "Save") }
        }

    }

}


@Preview(showBackground = true, showSystemUi =true)
@Composable
fun AddjobScreenPreview() {
    AddjobScreen(rememberNavController())
}
