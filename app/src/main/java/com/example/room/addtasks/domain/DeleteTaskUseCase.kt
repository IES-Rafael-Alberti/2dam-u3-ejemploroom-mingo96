package com.example.room.addtasks.domain
import com.example.room.addtasks.data.TaskRepository
import com.example.room.addtasks.ui.model.TaskModel as TaskModel2
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(taskModel: TaskModel2){
        taskRepository.delete(taskModel)
    }

}