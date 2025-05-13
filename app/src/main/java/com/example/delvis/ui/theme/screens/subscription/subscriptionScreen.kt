package com.example.delvis.ui.theme.screens.subscription

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.delvis.data.SubscriptionViewModel
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun SubscriptionScreen(navController: NavController) {
    val viewModel: SubscriptionViewModel = viewModel()
    val subscription by viewModel.subscription.collectAsState()
    val isSubscribed by viewModel.isSubscribed.collectAsState()
    val isPaying by viewModel.paymentInProgress.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(Color.White)
    ) {
        Text(
            text = subscription.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        subscription.features.forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color(0xFF6A00FF),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = it, fontSize = 16.sp)
            }
        }

        Card(
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .padding(vertical = 24.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "KES ${subscription.trialPrice}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "for ${subscription.trialDuration}",
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Afterwards KES ${subscription.regularPrice} / ${subscription.regularPeriod}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (subscription.autoRenew) "(automatically renewed)" else "",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        if (isSubscribed) {
            Text(
                text = "You're subscribed!",
                color = Color(0xFF4CAF50),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = { viewModel.cancelSubscription() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Cancel Subscription", fontSize = 16.sp, color = Color.White)
            }

        } else {
            Button(
                onClick = { viewModel.subscribe() },
                enabled = !isPaying,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A00FF))
            ) {
                Text(
                    text = if (isPaying) "Processing..." else "Pay",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val annotatedText = buildAnnotatedString {
            append("By continuing, you accept our ")
            pushStringAnnotation(tag = "terms", annotation = "https://example.com/terms")
            withStyle(style = SpanStyle(color = Color(0xFF6A00FF), fontWeight = FontWeight.Medium)) {
                append("terms")
            }
            pop()
            append(" and ")
            pushStringAnnotation(tag = "privacy", annotation = "https://example.com/privacy")
            withStyle(style = SpanStyle(color = Color(0xFF6A00FF), fontWeight = FontWeight.Medium)) {
                append("privacy policy")
            }
            pop()
            append(".")
        }

        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(offset, offset).firstOrNull()?.let {
                    // TODO: Handle link opening
                }
            },
            modifier = Modifier.padding(top = 8.dp),
            style = TextStyle(fontSize = 14.sp, color = Color.Gray)
        )
    }
}

