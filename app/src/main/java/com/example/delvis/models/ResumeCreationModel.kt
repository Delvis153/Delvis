package com.example.delvis.models

data class ResumeCreationModel(
    val name: String = "",
    val title: String = "",
    val email: String = "",
    val phone: String = "",
    val linkedIn: String = "",
    val summary: String = "",
    val education: List<Education> = emptyList(),
    val experience: List<Experience> = emptyList(),
    val skills: List<String> = emptyList()
)

data class Education(
    val institution: String = "",
    val degree: String = "",
    val graduationYear: String = ""
)

data class Experience(
    val company: String = "",
    val role: String = "",
    val period: String = "",
    val description: String = ""
)


