package com.example.delvis.ui.theme.screens.applications

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
import com.example.delvis.data.ProductViewModel
import com.example.delvis.R
import com.example.delvis.models.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
@Composable
fun UpdateApplicationScreen(navController: NavController, applicationId: String) {
    val context = LocalContext.current
    val imageUri = rememberSaveable() { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? -> uri?.let { imageUri.value = it } }

    var applicationName by remember { mutableStateOf("") }
    var applicationQuantity by remember { mutableStateOf("") }
    var applicationPrice by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    val currentDataRef = FirebaseDatabase.getInstance()
        .getReference().child("Applications/$applicationId")

    DisposableEffect(Unit) {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val application = snapshot.getValue(ProductModel::class.java)
                application?.let {
                    applicationName = it.productname
                    applicationQuantity = it.productquantity
                    applicationPrice = it.productprice
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

    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier.padding(10.dp).size(200.dp)
        ) {
            AsyncImage(
                model = imageUri.value ?: R.drawable.ic_person,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp)
                    .clickable { launcher.launch("image/*") }
            )
        }

        Text(text = "Attach application image")

        OutlinedTextField(
            value = applicationName,
            onValueChange = { newName -> applicationName = newName },
            label = { Text(text = "Application Name") },
            placeholder = { Text(text = "Please enter application name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = applicationQuantity,
            onValueChange = { newQty -> applicationQuantity = newQty },
            label = { Text(text = "Application Quantity") },
            placeholder = { Text(text = "Please enter application quantity") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = applicationPrice,
            onValueChange = { newPrice -> applicationPrice = newPrice },
            label = { Text(text = "Unit Application Price") },
            placeholder = { Text(text = "Please enter unit application price") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = desc,
            onValueChange = { newDesc -> desc = newDesc },
            label = { Text(text = "Brief description") },
            placeholder = { Text(text = "Please enter application description") },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            singleLine = false
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {}) { Text(text = "All Applications") }
            Button(onClick = {
                val applicationRepository = ProductViewModel() // Consider renaming class
                applicationRepository.updateProduct(
                    context = context,
                    navController = navController,
                    productname = applicationName,
                    productquantity = applicationQuantity,
                    productprice = applicationPrice,
                    desc = desc,
                    productId = applicationId
                )
            }) { Text(text = "UPDATE") }
        }
    }
}
