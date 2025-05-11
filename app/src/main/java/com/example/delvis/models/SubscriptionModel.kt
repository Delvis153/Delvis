package com.example.delvis.models

data class SubscriptionModel(
    val title: String = "Activate your subscription",
    val features: List<String> = listOf(
        "Create unlimited CVs",
        "Generate cover letters",
        "Alerts for job vacancies",
        "Keep track of your applications"
    ),
    val trialPrice: Double = 100.0,
    val trialDuration: String = "one month",
    val regularPrice: Double = 2200.0,
    val regularPeriod: String = "month",
    val autoRenew: Boolean = true
)
