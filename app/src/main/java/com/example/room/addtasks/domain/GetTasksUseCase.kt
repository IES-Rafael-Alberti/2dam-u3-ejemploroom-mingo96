package com.example.room.addtasks.domain

import com.example.room.addtasks.data.TaskModel
import com.example.room.addtasks.data.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke(): Flow<List<TaskModel>> =taskRepository.tasks

}