package com.example.delvis.ui.theme.screens.jobs

import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun JobSearchScreen(navController: NavController) {
    var position by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("Nairobi") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Jobs", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = position,
            onValueChange = { position = it },
            label = { Text("Desired job position") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { /* perform search */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filter tags
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, contentDescription = null)
            Text("25 km", modifier = Modifier.padding(end = 16.dp))

            Icon(painterResource(id = android.R.drawable.ic_menu_mapmode), contentDescription = null)
            Text("Worldwide")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Job Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "[...] Nairobi – Shared CFO & Por...",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "About [...] backs the world’s most ambitious founders from the very start...",
                    maxLines = 3,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Icon(Icons.Default.LocationOn, contentDescription = null)
                    Text("Nairobi", modifier = Modifier.padding(end = 16.dp))

                    Icon(painterResource(id = android.R.drawable.ic_menu_my_calendar), contentDescription = null)
                    Text("Full-time", modifier = Modifier.padding(end = 16.dp))

                    Icon(painterResource(id = android.R.drawable.ic_menu_recent_history), contentDescription = null)
                    Text("Today")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JobSearchScreenPreview() {
    JobSearchScreen(rememberNavController())
}
