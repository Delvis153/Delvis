package com.example.delvis.data

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.delvis.models.JobModel
import com.example.delvis.navigation.ROUTE_VIEW_JOBS
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class JobViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance().reference.child("Jobs")

    // Save Job without image
    fun saveJob(
        jobpositionname: String,
        joblocation: String,
        jobclosingdateforapplication: String,
        jobflexibility: String,
        navController: NavController
    ) {
        val jobId = database.push().key ?: return
        val job = JobModel(
            jobpositionname,
            joblocation,
            jobclosingdateforapplication,
            jobflexibility,
            imageUrl = "", // No image
            jobId = jobId
        )

        database.child(jobId).setValue(job)
            .addOnSuccessListener {
                Toast.makeText(navController.context, "Job saved successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(ROUTE_VIEW_JOBS)
            }
            .addOnFailureListener {
                Toast.makeText(navController.context, "Failed to save job", Toast.LENGTH_SHORT).show()
            }
    }

    // View Jobs
    fun viewJobs(
        job: MutableState<JobModel>,
        jobs: SnapshotStateList<JobModel>,
        context: Context
    ): SnapshotStateList<JobModel> {
        val ref = FirebaseDatabase.getInstance().getReference("Jobs")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                jobs.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(JobModel::class.java)
                    value?.let { jobs.add(it) }
                }
                if (jobs.isNotEmpty()) {
                    job.value = jobs.first()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch jobs: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
        return jobs
    }

    // Update Job
    fun updateJob(
        context: Context,
        navController: NavController,
        jobpositionname: String,
        joblocation: String,
        jobclosingdateforapplication: String,
        jobflexibility: String,
        jobId: String
    ) {
        val updatedJob = JobModel(
            jobpositionname,
            joblocation,
            jobclosingdateforapplication,
            jobflexibility,
            imageUrl = "", // No image
            jobId = jobId
        )

        database.child(jobId).setValue(updatedJob)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Job updated successfully", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_VIEW_JOBS)
                } else {
                    Toast.makeText(context, "Failed to update job", Toast.LENGTH_LONG).show()
                }
            }
    }

    // Delete Job
    fun deleteJob(context: Context, jobId: String, navController: NavController) {
        AlertDialog.Builder(context)
            .setTitle("Delete Job")
            .setMessage("Are you sure you want to delete this Job?")
            .setPositiveButton("Yes") { _, _ ->
                database.child(jobId).removeValue()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Job deleted successfully", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "Failed to delete job", Toast.LENGTH_LONG).show()
                        }
                    }
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
