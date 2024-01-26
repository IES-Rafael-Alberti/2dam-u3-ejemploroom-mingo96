package com.example.room.addtasks.ui


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room.addtasks.domain.AddTaskUseCase
import com.example.room.addtasks.domain.GetTasksUseCase
import com.example.room.addtasks.domain.DeleteTaskUseCase
import com.example.room.addtasks.domain.UpdateTaskUseCase
import com.example.room.addtasks.ui.TaskUiState.*
import com.example.room.addtasks.ui.model.TaskModel as UiTaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTasksUseCase: GetTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
): ViewModel() {


    val uiState: StateFlow<TaskUiState> = getTasksUseCase().map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskUiState.Loading)


    private val _showDialog =MutableLiveData<Boolean>(false)
    val showDialog:LiveData<Boolean> = _showDialog

    private val _myTaskDialect = MutableLiveData<String>("")
    val myTaskDialect:LiveData<String> = _myTaskDialect

    //private val _tasks = mutableStateListOf<TaskModel>()
    //val tasks: List<TaskModel> = _tasks


    fun closeDialog(){
        _showDialog.value = false
    }

    fun openDialog(){
        _showDialog.value = true
    }

    fun taskCreated(){
        closeDialog()
        //guardo el valor en una constante para que no se borre antes de añadirse, cosa que pasa por ser asincrono
        val tarea = "${_myTaskDialect.value}"
        viewModelScope.launch(context = Dispatchers.IO){
            addTaskUseCase(UiTaskModel(task = tarea , selected = false))
        }
        changeText("")


    }

    fun removeItem(taskModel: UiTaskModel){

        viewModelScope.launch (context = Dispatchers.IO) {
            deleteTaskUseCase(taskModel)
        }

    }

    fun onCheckBoxSelected(taskModel: UiTaskModel) {
        taskModel.selected = !taskModel.selected

        viewModelScope.launch(context = Dispatchers.IO){
            updateTaskUseCase(taskModel)
        }


    }

    fun changeText(newText:String){
        _myTaskDialect.value = newText
    }
}

