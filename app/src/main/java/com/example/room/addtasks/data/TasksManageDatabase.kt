package com.example.room.addtasks.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Database(entities = [TaskEntity::class], version = 1)
abstract class TasksManageDatabase: RoomDatabase() {
    //DAO
    abstract fun taskDao():TaskDao
}

data class TaskEntity (
    @PrimaryKey
    val id:Int,
    val task:String,
    var selected:Boolean=false
)

data class TaskModel(
    val id:Int = System.currentTimeMillis().hashCode(),
    val task: String,
    var selected: Boolean
)

@Dao
interface TaskDao{
    @Query("SELECT * FROM TaskEntity")
    fun getTasks(): Flow<List<TaskEntity>>

    @Insert
    suspend fun addTask(item:TaskEntity)
}