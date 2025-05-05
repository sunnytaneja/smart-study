package com.android.smartstudy.ui.presentation.subject

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smartstudy.domain.model.Subject
import com.android.smartstudy.domain.repository.SessionRepository
import com.android.smartstudy.domain.repository.SubjectRepository
import com.android.smartstudy.domain.repository.TaskRepository
import com.android.smartstudy.ui.presentation.navArgs
import com.android.smartstudy.util.SnackbarEvent
import com.android.smartstudy.util.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val taskRepository: TaskRepository,
    private val sessionRepository: SessionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val navArgs: SubjectScreenNavArgs = savedStateHandle.navArgs()
    private val _state = MutableStateFlow(SubjectState())

    init {
        fetchSubject()
    }

    val state = combine(
        _state,
        taskRepository.getUpcomingTasksForSubject(navArgs.subjectId),
        taskRepository.getCompletedTasksForSubject(navArgs.subjectId),
        sessionRepository.getRecentTenSessionsForSubject(navArgs.subjectId),
        sessionRepository.getTotalSessionsDurationBySubject(navArgs.subjectId)
    ) { state, upcomingTasks, completedTask, recentSession, totalSessionDuration ->
        state.copy(
            upcomingTasks = upcomingTasks,
            completeTasks = completedTask,
            recentSession = recentSession,
            studiedHours = totalSessionDuration.toHours()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = SubjectState()
    )

    private val _snackbarEventFlow = MutableSharedFlow<SnackbarEvent>()
    val snackbarEventFlow = _snackbarEventFlow.asSharedFlow()

    fun onEvent(event: SubjectEvent) {
        when (event) {
            SubjectEvent.DeleteSession -> TODO()
            SubjectEvent.DeleteSubject -> deleteSubject()
            is SubjectEvent.OnDeleteSessionButtonClick -> TODO()
            is SubjectEvent.OnGoalStudyHoursChange -> _state.update {
                it.copy(goalStudyHours = event.hours)
            }

            is SubjectEvent.OnSubjectCardColorChange -> _state.update {
                it.copy(subjectCardColor = event.color)
            }

            is SubjectEvent.OnSubjectNameChange -> _state.update {
                it.copy(subjectName = event.name)
            }

            is SubjectEvent.OnTaskIsCompletedChange -> TODO()
            SubjectEvent.UpdateSubject -> updateSubject()
            SubjectEvent.UpdateProgress -> {
                val goalStudyHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f
                _state.update {
                    it.copy(progress = (state.value.studiedHours / goalStudyHours).coerceIn(0f, 1f))
                }
            }
        }
    }

    private fun updateSubject() {
        try {
            viewModelScope.launch {
                subjectRepository.upsertSubject(
                    subject = Subject(
                        subjectId = state.value.currentSubjectId,
                        name = state.value.subjectName,
                        goalHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f,
                        color = state.value.subjectCardColor.map { it.toArgb() }
                    ))
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar("Subject updated successfully")
                )
            }
        } catch (e: Exception) {
//            _snackbarEventFlow.emit(
//                SnackbarEvent.ShowSnackbar(
//                    message = "Couldn't save subject. ${e.message}",
//                    duration = SnackbarDuration.Long
//                )
//            )
        }
    }

    private fun fetchSubject() {
        viewModelScope.launch {
            subjectRepository.getSubjectById(navArgs.subjectId)?.let { subject ->
                _state.update {
                    it.copy(
                        subjectName = subject.name,
                        goalStudyHours = subject.goalHours.toString(),
                        subjectCardColor = subject.color.map { Color(it) },
                        currentSubjectId = subject.subjectId
                    )
                }
            }
        }
    }

    private fun deleteSubject() {
        viewModelScope.launch {
//            _state.update { it.copy(isLoading = true) }
            try {
                val currentSubjectId = state.value.currentSubjectId
                if (currentSubjectId != null) {
                    withContext(Dispatchers.IO) {
                        subjectRepository.deleteSubject(subjectId = currentSubjectId)
                    }
                    /*state.value.currentSubjectId?.let {
                        subjectRepository.deleteSubject(it)
                    }*/
                    _snackbarEventFlow.emit(
                        SnackbarEvent.ShowSnackbar("Subject delete successfully")
                    )
                    _snackbarEventFlow.emit(SnackbarEvent.NavigateUp)
                } else {
                    _snackbarEventFlow.emit(
                        SnackbarEvent.ShowSnackbar("No Subject to delete")
                    )
                }
            } catch (e: Exception) {
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        "Couldn't delete subject. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }
}