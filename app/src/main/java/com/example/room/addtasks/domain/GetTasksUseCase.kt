package com.example.room.addtasks.domain

import com.example.room.addtasks.ui.model.TaskModel

import com.example.room.addtasks.data.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke(): Flow<List<TaskModel>> =taskRepository.tasks.map { it -> it.map {TaskModel(it.id, it.task, it.selected) }}

}