package com.example.delvis.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Your correct model class
data class Subscription(
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

class SubscriptionViewModel : ViewModel() {

    private val _subscription = MutableStateFlow(Subscription())
    val subscription: StateFlow<Subscription> = _subscription

    private val _isSubscribed = MutableStateFlow(false)
    val isSubscribed: StateFlow<Boolean> = _isSubscribed

    private val _paymentInProgress = MutableStateFlow(false)
    val paymentInProgress: StateFlow<Boolean> = _paymentInProgress

    fun subscribe() {
        _paymentInProgress.value = true

        // Simulate payment logic (in real apps, use a coroutine or repository call)
        _isSubscribed.value = true
        _paymentInProgress.value = false
    }

    fun cancelSubscription() {
        _isSubscribed.value = false
    }
}
