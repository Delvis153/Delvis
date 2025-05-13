package com.example.delvis.models

// Data class for the Resume
data class ResumePreviewModel(
    val name: String = "",
    val title: String = "",
    val email: String = "",
    val phone: String = "",
    val linkedIn: String = "",
    val summary: String = "",
    val education: List<AcademicEducation> = emptyList(),
    val experience: List<JobExperience> = emptyList(),
    val skills: List<String> = emptyList()
)

// Data class for Education details (Renamed to AcademicEducation to avoid conflict)
data class AcademicEducation(
    val institution: String = "",
    val degree: String = "",
    val graduationYear: String = ""
)

// Data class for Job experience details (Renamed to JobExperience)
data class JobExperience(
    val company: String = "",
    val role: String = "",
    val period: String = "",
    val description: String = ""
)
