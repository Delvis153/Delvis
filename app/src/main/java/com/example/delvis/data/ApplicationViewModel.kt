package com.example.delvis.data


import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.delvis.models.ApplicationModel
import com.example.delvis.navigation.ROUTE_VIEW_APPLICATION
import com.example.delvis.network.ImgurService
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class ApplicationViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance().reference.child("Applications")

    private fun getImgurService(): ImgurService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgur.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ImgurService::class.java)
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile("temp_image", ".jpg", context.cacheDir)
            file.outputStream().use { output ->
                inputStream?.copyTo(output)
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun uploadApplicationWithImage(
        uri: Uri,
        context: Context,
        applicantsName: String,
        applicantsGender: String,
        applicantsDesiredjob: String,
        applicantsExperience: String,
        desc: String,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = getFileFromUri(context, uri)
                if (file == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Failed to process image", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", file.name, reqFile)
                val response = getImgurService().uploadImage(
                    body,
                    "Client-ID 4a9cd0ac9fd5d4f"
                )

                if (response.isSuccessful) {
                    val imageUrl = response.body()?.data?.link ?: ""
                    val applicationId = database.push().key ?: ""
                    val application = ApplicationModel(
                        applicantsName,
                        applicantsGender,
                        applicantsDesiredjob,
                        applicantsExperience,
                        desc,
                        imageUrl,
                        applicationId
                    )
                    database.child(applicationId).setValue(application)
                        .addOnSuccessListener {
                            viewModelScope.launch {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Application saved successfully", Toast.LENGTH_SHORT).show()
                                    navController.navigate(ROUTE_VIEW_APPLICATION)
                                }
                            }
                        }.addOnFailureListener {
                            viewModelScope.launch {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Failed to save application", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Upload error", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Exception: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun viewApplications(
        application: MutableState<ApplicationModel>,
        applications: SnapshotStateList<ApplicationModel>,
        context: Context
    ): SnapshotStateList<ApplicationModel> {
        val ref = FirebaseDatabase.getInstance().getReference("Applications")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                applications.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(ApplicationModel::class.java)
                    value?.let {
                        applications.add(it)
                    }
                }
                if (applications.isNotEmpty()) {
                    application.value = applications.first()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch applications: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
        return applications
    }

    fun updateApplication(
        context: Context,
        navController: NavController,
        applicantsName: String,
        applicantsGender: String,
        applicantsDesiredjob: String,
        applicantsExperience: String,
        desc: String,
        applicationId: String,
        imageUri: Uri?
    ) {
        val databaseReference = FirebaseDatabase.getInstance()
            .getReference("Applications/$applicationId")
        val updatedApp = ApplicationModel(applicantsName, applicantsGender, applicantsDesiredjob, applicantsExperience, desc, "", applicationId)
        databaseReference.setValue(updatedApp)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Application Updated Successfully", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_VIEW_APPLICATION)
                } else {
                    Toast.makeText(context, "Application update failed", Toast.LENGTH_LONG).show()
                }
            }
    }


    fun deleteApplication(
        context: Context,
        applicationId: String,
        navController: NavController
    ) {
        AlertDialog.Builder(context)
            .setTitle("Delete Application")
            .setMessage("Are you sure you want to delete this application?")
            .setPositiveButton("Yes") { _, _ ->
                val databaseReference = FirebaseDatabase.getInstance()
                    .getReference("Applications/$applicationId")
                databaseReference.removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Application deleted successfully", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Application not deleted", Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}


