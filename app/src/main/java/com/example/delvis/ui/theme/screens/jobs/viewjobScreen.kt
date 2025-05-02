package com.example.delvis.ui.theme.screens.jobs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.delvis.data.JobViewModel
import com.example.delvis.models.JobModel

@Composable
fun ViewJobs(navController: NavHostController) {
    val context = LocalContext.current
    val jobRepository = JobViewModel()
    val emptyJobState = remember {
        mutableStateOf(JobModel("", "", "", "", "", ""))
    }
    val jobListState = remember {
        mutableStateListOf<JobModel>()
    }
    val jobs = jobRepository.viewJobs(emptyJobState, jobListState, context)

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "All Jobs",
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            items(jobs) {
                JobItem(
                    jobpositionname = it.jobpositionname,
                    joblocation = it.joblocation,
                    jobclosingdateforapplication = it.jobclosingdateforapplication,
                    jobflexibility = it.jobflexibility,
                    jobId = it.jobId,
                    imageUrl = it.imageUrl,
                    navController = navController,
                    jobRepository = jobRepository
                )
            }
        }
    }
}

@Composable
fun JobItem(
    jobpositionname: String,
    joblocation: String,
    jobclosingdateforapplication: String,
    jobflexibility: String,
    jobId: String,
    imageUrl: String,
    navController: NavHostController,
    jobRepository: JobViewModel
) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .padding(10.dp)
                .height(210.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = Color.Gray)
        ) {
            Row {
                Column {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(200.dp)
                            .height(150.dp)
                            .padding(10.dp)
                    )
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(
                            onClick = {
                                jobRepository.deleteJob(context, jobId, navController)
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Color.Red)
                        ) {
                            Text(
                                text = "REMOVE",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        Button(
                            onClick = {
                                navController.navigate("update_job/$jobId")
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Color.Green)
                        ) {
                            Text(
                                text = "UPDATE",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 10.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text("JOB POSITION NAME", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(jobpositionname, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Text("JOB LOCATION", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(joblocation, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Text("JOB CLOSING DATE", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(jobclosingdateforapplication, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Text("JOB FLEXIBILITY", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(jobflexibility, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview
@Composable
fun ViewJobsPreview() {
    ViewJobs(rememberNavController())
}

