package com.example.delvis.ui.theme.screens.applications

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController


@Composable
fun ApplicationBoard(navController: NavHostController) {
    val categories = listOf("Favorites", "Applied", "Interviews", "Offers", "Rejected", "Hired") // You can expand this list
    val applicationsMap = mapOf(
        "Favorites" to listOf("App 1", "App 2"),
        "Applied" to listOf("App 3"),
        "Interviews" to listOf("Interview 1"),
        "Offers" to listOf("Offer 1", "Offer 2"),
        "Rejected" to listOf("Reject 1"),
        "Hired" to listOf("Hired 1", "Hired 2"),
    )

    LazyRow (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(categories) { category ->
            Column(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .width(250.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = category,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    IconButton(onClick = { /* Add action */ }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                applicationsMap[category]?.forEach { appName ->
                    ApplicationCard(appName)
                }
            }
        }
    }
}

@Composable
fun ApplicationCard(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = name,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 16.sp
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ApplicationBoardPreview() {
    ApplicationBoard(rememberNavController())
}
