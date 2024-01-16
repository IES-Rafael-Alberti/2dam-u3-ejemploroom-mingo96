package com.example.room.addtasks.domain

import com.example.room.addtasks.data.TaskModel
import com.example.room.addtasks.data.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(taskModel: TaskModel){
        taskRepository.add(taskModel)
    }

}