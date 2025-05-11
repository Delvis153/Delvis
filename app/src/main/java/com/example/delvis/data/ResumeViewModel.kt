package com.example.delvis.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.delvis.models.DocumentItem
import androidx.compose.runtime.State
import com.example.delvis.models.DocumentType

data class ResumeScreenModel(
    val resumes: List<DocumentItem>,
    val coverLetters: List<DocumentItem>
)

class ResumeViewModel : ViewModel() {
    private val _screenModel = mutableStateOf<ResumeScreenModel>(
        ResumeScreenModel(
            resumes = listOf(
                DocumentItem("1", "Software Engineer Resume", "2025-05-09", DocumentType.RESUME)
            ),
            coverLetters = listOf(
                DocumentItem("2", "Marketing Cover Letter", "2025-05-01", DocumentType.COVER_LETTER)
            )
        )
    )

    val screenModel: State<ResumeScreenModel> = _screenModel
}

