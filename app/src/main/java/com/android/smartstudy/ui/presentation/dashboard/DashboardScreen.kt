package com.android.smartstudy.ui.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.smartstudy.R
import com.android.smartstudy.domain.model.Session
import com.android.smartstudy.domain.model.Subject
import com.android.smartstudy.domain.model.Task
import com.android.smartstudy.ui.presentation.components.AddSubjectDialog
import com.android.smartstudy.ui.presentation.components.CountCard
import com.android.smartstudy.ui.presentation.components.DeleteDialog
import com.android.smartstudy.ui.presentation.components.SubjectCard
import com.android.smartstudy.ui.presentation.components.studySessionsList
import com.android.smartstudy.ui.presentation.components.tasksList

@Composable
fun DashboardScreen() {
    val subjectsList = listOf(
        Subject(name = "English", goalHours = 10f, color = Subject.subjectCardColors[0], 0),
        Subject(name = "Physics", goalHours = 10f, color = Subject.subjectCardColors[1], 0),
        Subject(name = "Maths", goalHours = 10f, color = Subject.subjectCardColors[2], 0),
        Subject(name = "Golang", goalHours = 10f, color = Subject.subjectCardColors[3], 0),
        Subject(name = "Fine Arts", goalHours = 10f, color = Subject.subjectCardColors[4], 0)

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

    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }

    var subjectName by remember {
        mutableStateOf("")
    }

    var goalHours by remember {
        mutableStateOf("")
    }
    var selectedColor by remember {
        mutableStateOf(Subject.subjectCardColors.random())
    }

    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        subjectName = subjectName,
        goalHours = goalHours,
        selectedColors = selectedColor,
        onSubjectNameChange = { subjectName = it },
        onGoalHoursChange = { goalHours = it },
        onColorChange = { selectedColor = it },
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButtonClick = {
//            onEvent(DashboardEvent.SaveSubject)
            isAddSubjectDialogOpen = false
        }
    )

    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        title = "Delete Session?",
        bodyText = "Are you sure, you want to delete this session? Your studied hours will be reduced " +
                "by this session time. This action can not be undone.",
        onDismissRequest = { isDeleteSessionDialogOpen = false },
        onConfirmButtonClick = {
//            onEvent(DashboardEvent.DeleteSession)
            isDeleteSessionDialogOpen = false
        }
    )
    Scaffold(topBar = { DashboardScreenTopBar() }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                CountCardsSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    subjectCount = 5,
                    studiedHours = "10",
                    goalHours = "15"
                )
            }
            item {
                SubjectCardsSection(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = subjectsList,
                    onAddIconClicked = {
                        isAddSubjectDialogOpen = true
                    })
            }

            item {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)
                ) {
                    Text(text = "Start Study Session")
                }
            }

            tasksList(
                sectionTitle = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks.\n " +
                        "Click the + button in subject screen to add new task.",
                tasks = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = {}
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            studySessionsList(
                sectionTitle = "RECENT STUDY SESSION",
                emptyListText = "You don't have any recent study sessions.\n " +
                        "Start a study session to begin recording your progress.",
                session = sessionsList,
                onDeleteIconClick = { isDeleteSessionDialogOpen = true }
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenTopBar() {
    CenterAlignedTopAppBar(title = {
        Text(text = "SmartStudy", style = MaterialTheme.typography.headlineMedium)
    })
}

@Composable
private fun CountCardsSection(
    modifier: Modifier, subjectCount: Int,
    studiedHours: String, goalHours: String,
) {
    Row {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Subject Count",
            count = "$subjectCount"
        )

        Spacer(modifier = Modifier.width(10.dp))

        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Studied Count",
            count = studiedHours
        )

        Spacer(modifier = Modifier.width(10.dp))

        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            count = goalHours
        )
    }
}

@Composable
private fun SubjectCardsSection(
    modifier: Modifier,
    subjectList: List<Subject>,
    emptyListText: String = "You don't have any subjects.\n click the + button to add new subject",
    onAddIconClicked: () -> Unit,
) {
    Column(modifier = Modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SUBJECTS", style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp)
            )
            IconButton(onClick = onAddIconClicked) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Subject")
            }
        }

        if (subjectList.isEmpty()) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally), painter = painterResource(
                    id = R.drawable.img_books
                ), contentDescription = emptyListText
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = emptyListText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList) { subject ->
                SubjectCard(subjectName = subject.name, gradientColor = subject.color,
                    onClick = {})
            }
        }
    }
}
