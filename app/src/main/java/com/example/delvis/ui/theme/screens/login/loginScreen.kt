package com.example.delvis.ui.theme.screens.login

//import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.delvis.data.AuthViewModel
//import com.example.delvis.navigation.ROUTE_LOGIN
import com.example.delvis.navigation.ROUTE_REGISTER

@Composable
fun LoginScreen(navController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()
    var name by remember { mutableStateOf(value = "") }
    var email by remember { mutableStateOf(value = "") }
    var password by remember { mutableStateOf(value = "") }
    val context = LocalContext.current 

//    var googleCardHovered by remember { mutableStateOf(false) }
    var icloudCardHovered by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),  
        verticalArrangement = Arrangement.Center,

    ) {
        Text(
            text = "Log in",
            fontSize = 40.sp,
            color = Color.White,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(bottom = 8.dp)  // Adjusted padding
        )

        // Google and iCloud login options (Row with Cards)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp), // Padding for space between Row and next components
            horizontalArrangement = Arrangement.Center
        ) {
            // Google Card
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(Color.DarkGray),
            ) {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(25.dp),
                    contentAlignment = Alignment.Center,

                ) {
                    Text(text = "Google", color = Color.White)  // Text in the center of the Card
                }
            }

            // iCloud Card
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(
                    if (icloudCardHovered) Color.Magenta else Color.DarkGray),


                ) {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(25.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Icloud", color = Color.White)  // Text in the center of the Card
                }
            }
        }

        Text(
            text = "Login with one of the following options.",
            fontSize = 15.sp,
            color = Color.White,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(bottom = 16.dp)// Adjusted padding
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { newName -> name = newName },
            label = { Text(text = "Enter name") },
            placeholder = { Text(text = "Please enter your Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Email Icon")}
        )


        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            label = { Text(text = "Enter email") },
            placeholder = { Text(text = "Please enter your email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            leadingIcon = { Icon(imageVector = Icons.Default.MailOutline, contentDescription = "Email Icon")}
        )


        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { newPassword -> password = newPassword },
            label = { Text(text = "Enter password") },
            placeholder = { Text(text = "Please enter your password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock Icon")}
        )


        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                authViewModel.login(email, password, navController, context)
            },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(Color.Magenta),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = "Log in")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = buildAnnotatedString { append("Don't have an account? Sign up") },
            modifier = Modifier
                .clickable {
                    navController.navigate(ROUTE_REGISTER)
                }
                .align(Alignment.CenterHorizontally),
            color = Color.White
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen(rememberNavController())
}
