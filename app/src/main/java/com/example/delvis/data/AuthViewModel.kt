package com.example.delvis.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.delvis.models.UserModel
import com.example.delvis.navigation.ROUTE_DASHBOARD
import com.example.delvis.navigation.ROUTE_LOGIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel: ViewModel() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    fun signup(firstname: String,
               middlename: String,
               lastname: String,
               email: String,
               password: String,
               navController: NavController,
               context: Context
    ){
//        ||means or
        if (firstname.isBlank() || middlename.isBlank() || lastname.isBlank() || email.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Please enter all fields", Toast.LENGTH_LONG).show()

            return
        }
        _isLoading.value = true

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    val userId = mAuth.currentUser?.uid ?: ""
                    val userData = UserModel(
                        firstname = firstname,
//                        middlename = middlename,
                        lastname = lastname,
                        email = email,
                        password = password,
                        userId = userId
                    )
                    saveUserToDatabase(userId, userData, navController, context)
                }
                else {
                    _errorMessage.value = task.exception?.message
                    Toast.makeText(context, "Registration Failed", Toast.LENGTH_LONG).show()

                }

            }
    }
    fun saveUserToDatabase(userId: String,
                           userData: UserModel,
                           navController: NavController,
                           context: Context
    ){
        val regRef = FirebaseDatabase.getInstance()
            .getReference("Users/$userId")
        regRef.setValue(userData).addOnCompleteListener { regRef ->
            if(regRef.isSuccessful){
                Toast.makeText(context, "Registration Successful", Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_LOGIN)
            }
            else {
                _errorMessage.value = regRef.exception?.message
                Toast.makeText(context, "Database Error", Toast.LENGTH_LONG).show()

            }
        }
    }

    fun login(email: String, password: String, navController: NavController, context: Context){
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Please enter all fields", Toast.LENGTH_LONG).show()
            return
        }
        _isLoading.value = true

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {

                    Toast.makeText(context, "User Logged in Successfully", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_DASHBOARD)
                }
                else {
                    _errorMessage.value = task.exception?.message
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show()


                }
            }

    }
}








