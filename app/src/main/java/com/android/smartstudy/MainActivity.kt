package com.android.smartstudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.toArgb
import com.android.smartstudy.domain.model.Session
import com.android.smartstudy.domain.model.Subject
import com.android.smartstudy.domain.model.Task
import com.android.smartstudy.ui.presentation.NavGraphs
import com.android.smartstudy.ui.theme.SmartStudyTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartStudyTheme {

                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

val subjectsList = listOf(
    Subject(name = "English", goalHours = 10f, color = Subject.subjectCardColors[0].map { it.toArgb() }, 0),
    Subject(name = "Physics", goalHours = 10f, color = Subject.subjectCardColors[1].map { it.toArgb() }, 0),
    Subject(name = "Maths", goalHours = 10f, color = Subject.subjectCardColors[2].map { it.toArgb() }, 0),
    Subject(name = "Golang", goalHours = 10f, color = Subject.subjectCardColors[3].map { it.toArgb() }, 0),
    Subject(name = "Fine Arts", goalHours = 10f, color = Subject.subjectCardColors[4].map { it.toArgb() }, 0)
)

val tasks = listOf(
    Task(
        title = "Proper Notes", description = "",
        dueDate = 0L, priority = 0,
        relatedToSubjects = "",
        isComplete = false,
        0, 1
    ),

    Task(
        title = "Do Homework",
        description = "",
        dueDate = 0L, priority = 1,
        relatedToSubjects = "",
        isComplete = true, 0, 1
    ),
    Task(
        title = "Go Coaching",
        description = "",
        dueDate = 0L, priority = 2,
        relatedToSubjects = "",
        isComplete = false, 0, 1
    ),
    Task(
        title = "Assignment",
        description = "",
        dueDate = 0L, priority = 1,
        relatedToSubjects = "",
        isComplete = false, 0, 1
    ),
    Task(
        title = "Writing",
        description = "",
        dueDate = 0L, priority = 1,
        relatedToSubjects = "",
        isComplete = true, 0, 1
    ),
)

val sessionsList = listOf(
    Session(
        relatedToSubject = "English",
        date = 0L,
        duration = 2,
        sessionSubjectId = 0,
        sessionId = 0
    ),
    Session(
        relatedToSubject = "Physics",
        date = 0L,
        duration = 2,
        sessionSubjectId = 0,
        sessionId = 0
    ),
    Session(
        relatedToSubject = "Maths",
        date = 0L,
        duration = 2,
        sessionSubjectId = 0,
        sessionId = 0
    ),
    Session(
        relatedToSubject = "English",
        date = 0L,
        duration = 2,
        sessionSubjectId = 0,
        sessionId = 0
    ),
    Session(
        relatedToSubject = "English",
        date = 0L,
        duration = 2,
        sessionSubjectId = 0,
        sessionId = 0
    )

)