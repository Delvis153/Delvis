package com.example.delvis.models


enum class DocumentType {
    RESUME,
    COVER_LETTER
}

data class DocumentItem(
    val id: String,
    val title: String,
    val createdDate: String,
    val type: DocumentType
)

data class ResumeModel(
    val resumes: List<DocumentItem> = emptyList(),
    val coverLetters: List<DocumentItem> = emptyList()
)



