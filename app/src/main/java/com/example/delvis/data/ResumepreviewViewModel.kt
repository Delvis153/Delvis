package com.example.delvis.data


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.delvis.models.AcademicEducation  // Renamed from Education
import com.example.delvis.models.JobExperience     // Renamed from Experience
import com.example.delvis.models.ResumePreviewModel

class ResumePreviewViewModel : ViewModel() {

    var resume = mutableStateOf(
        ResumePreviewModel(
            name = "John Doe",
            title = "Android Developer",
            email = "johndoe@example.com",
            phone = "+1 234 567 890",
            linkedIn = "linkedin.com/in/johndoe",
            summary = "Passionate Android Developer with 5+ years of experience building high-quality mobile applications using Kotlin and Jetpack Compose.",
            education = listOf(
                AcademicEducation("University of XYZ", "B.Sc. in Computer Science", "2018") // Updated class name
            ),
            experience = listOf(
                JobExperience(  // Updated class name
                    company = "ABC Tech",
                    role = "Senior Android Developer",
                    period = "2020 - Present",
                    description = "Developed and maintained multiple Android apps using MVVM and Jetpack Compose."
                ),
                JobExperience(  // Updated class name
                    company = "XYZ Corp",
                    role = "Android Developer",
                    period = "2018 - 2020",
                    description = "Built e-commerce app with Firebase backend. Improved app performance and UI responsiveness."
                )
            ),
            skills = listOf("Kotlin", "Jetpack Compose", "Firebase", "MVVM", "Git")
        )
    )

    // You can update or manipulate the resume data here if needed.
}
