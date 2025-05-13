package com.example.delvis.data


import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.delvis.models.ApplicationModel
import com.example.delvis.navigation.ROUTE_VIEW_APPLICATION
import com.google.firebase.database.*

class ApplicationViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance().reference.child("Applications")
    private val _jobList = mutableStateListOf<String>()
    val jobList: List<String> get() = _jobList

    init {
        fetchJobsFromFirebase()
    }

    private fun fetchJobsFromFirebase() {
        val dbRef = FirebaseDatabase.getInstance().getReference("Jobs")
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _jobList.clear()
                for (jobSnap in snapshot.children) {
                    val jobTitle = jobSnap.child("jobTitle").getValue(String::class.java)
                    jobTitle?.let { _jobList.add(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ApplicationViewModel", "Error loading jobs: ${error.message}")
            }
        })
    }


    fun saveApplication(
        context: Context,
        applicantsName: String,
        applicantsGender: String,
        applicantsDesiredjob: String,
        applicantsExperience: String,
        desc: String,
        navController: NavController
    ) {
        val applicationId = database.push().key ?: return

        val application = ApplicationModel(
            applicantsName = applicantsName,
            applicantsGender = applicantsGender,
            applicantsDesiredjob = applicantsDesiredjob,
            applicantsExperience = applicantsExperience,
            desc = desc,
            imageUrl = "", // No image URL
            applicationId = applicationId
        )

        database.child(applicationId).setValue(application)
            .addOnSuccessListener {
                Toast.makeText(context, "Application saved successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(ROUTE_VIEW_APPLICATION)
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to save application", Toast.LENGTH_SHORT).show()
            }
    }

    fun viewApplications(
        application: MutableState<ApplicationModel>,
        applications: SnapshotStateList<ApplicationModel>,
        context: Context
    ): SnapshotStateList<ApplicationModel> {
        database.addValueEventListener(object : ValueEventListener {
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
        applicationId: String
    ) {
        val updatedApp = ApplicationModel(
            applicantsName = applicantsName,
            applicantsGender = applicantsGender,
            applicantsDesiredjob = applicantsDesiredjob,
            applicantsExperience = applicantsExperience,
            desc = desc,
            imageUrl = "", // No image
            applicationId = applicationId
        )

        database.child(applicationId).setValue(updatedApp)
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
                database.child(applicationId).removeValue()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Application deleted successfully", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "Application not deleted", Toast.LENGTH_LONG).show()
                        }
                    }
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
