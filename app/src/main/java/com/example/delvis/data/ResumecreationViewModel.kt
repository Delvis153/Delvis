package com.example.delvis.data

//import androidx.compose.runtime.*
//import androidx.lifecycle.ViewModel
//import com.example.delvis.models.Education
//import com.example.delvis.models.Experience
//import com.example.delvis.models.ResumeCreationModel

//class ResumeCreationViewModel : ViewModel() {
//
//    var resume by mutableStateOf(ResumeCreationModel())
//        private set
//
//    fun updateName(newName: String) {
//        resume = resume.copy(name = newName)
//    }
//
//    fun updateTitle(newTitle: String) {
//        resume = resume.copy(title = newTitle)
//    }
//
//    fun updateEmail(newEmail: String) {
//        resume = resume.copy(email = newEmail)
//    }
//
//    fun updatePhone(newPhone: String) {
//        resume = resume.copy(phone = newPhone)
//    }
//
//    fun updateLinkedIn(newLink: String) {
//        resume = resume.copy(linkedIn = newLink)
//    }
//
//    fun updateSummary(newSummary: String) {
//        resume = resume.copy(summary = newSummary)
//    }
//
//    fun addEducation(education: Education) {
//        resume = resume.copy(education = resume.education + education)
//    }
//
//    fun addExperience(experience: Experience) {
//        resume = resume.copy(experience = resume.experience + experience)
//    }
//
//    fun addSkill(skill: String) {
//        resume = resume.copy(skills = resume.skills + skill)
//    }
//
//    fun removeSkill(skill: String) {
//        resume = resume.copy(skills = resume.skills - skill)
//    }
//
//    fun clearResume() {
//        resume = ResumeCreationModel()
//    }
//}



import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
//import com.example.delvis.model.*
import com.example.delvis.models.Education
import com.example.delvis.models.Experience
import com.example.delvis.models.ResumeCreationModel
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class ResumeCreationViewModel : ViewModel() {

    var resume by mutableStateOf(ResumeCreationModel())
        private set

    fun updateName(newName: String) { resume = resume.copy(name = newName) }
    fun updateTitle(newTitle: String) { resume = resume.copy(title = newTitle) }
    fun updateEmail(newEmail: String) { resume = resume.copy(email = newEmail) }
    fun updatePhone(newPhone: String) { resume = resume.copy(phone = newPhone) }
    fun updateLinkedIn(newLink: String) { resume = resume.copy(linkedIn = newLink) }
    fun updateSummary(newSummary: String) { resume = resume.copy(summary = newSummary) }

    fun addEducation(education: Education) {
        resume = resume.copy(education = resume.education + education)
    }

    fun addExperience(experience: Experience) {
        resume = resume.copy(experience = resume.experience + experience)
    }

    fun addSkill(skill: String) {
        resume = resume.copy(skills = resume.skills + skill)
    }

    fun saveToFirebase(userId: String? = null, onSuccess: () -> Unit = {}, onError: (Exception) -> Unit = {}) {
        val db = FirebaseDatabase.getInstance().reference
        val id = userId ?: UUID.randomUUID().toString()

        db.child("resumes").child(id)
            .setValue(resume)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }
}
