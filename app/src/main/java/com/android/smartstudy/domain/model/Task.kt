package com.android.smartstudy.domain.model

data class Task(
    val title: String,
    val description: String,
    val dueDate: Long,
    val priority: Int,
    val relatedToSubjects: String,
    val isComplete: Boolean,
    val taskSubjectId:Int,
    val taskId: Int,
)
