package com.android.smartstudy.ui.presentation.task

import com.android.smartstudy.domain.model.Subject
import com.android.smartstudy.util.Priority

data class TaskState(

    val title: String = "",
    val description: String = "",
    val dueDate: Long? = null,
    val isTaskComplete: Boolean = true,
    val priority: Priority = Priority.LOW,
    val relatedToSubject: String? = null,
    val subjects: List<Subject> = emptyList(),
    val subjectId: Int? = null,
    val currentTaskId: Int? = null,
)
