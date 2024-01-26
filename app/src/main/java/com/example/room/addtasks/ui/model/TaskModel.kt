package com.example.room.addtasks.ui.model

data class TaskModel(
    val id: Int = System.currentTimeMillis().toInt(),
    val task: String,
    var selected: Boolean = false
)