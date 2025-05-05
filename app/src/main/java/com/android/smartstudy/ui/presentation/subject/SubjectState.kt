package com.android.smartstudy.ui.presentation.subject

import androidx.compose.ui.graphics.Color
import com.android.smartstudy.domain.model.Session
import com.android.smartstudy.domain.model.Subject
import com.android.smartstudy.domain.model.Task

data class SubjectState(
    val currentSubjectId: Int? = null,
    val subjectName: String = "",
    val goalStudyHours: String = "",
    val studiedHours: Float = 0f,
    val subjectCardColor: List<Color> = Subject.subjectCardColors.random(),
    val recentSession: List<Session> = emptyList(),
    val upcomingTasks: List<Task> = emptyList(),
    val completeTasks: List<Task> = emptyList(),
    val session: Session? = null,
    val progress: Float = 0f,
    val isLoading: Boolean = false,
)
