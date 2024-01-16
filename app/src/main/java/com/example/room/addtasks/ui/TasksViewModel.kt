package com.example.room.addtasks.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room.addtasks.domain.AddTaskUseCase
import com.example.room.addtasks.domain.GetTasksUseCase
import com.example.room.addtasks.ui.TaskUiState.Success
import com.example.room.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    getTasksUseCase: GetTasksUseCase
): ViewModel() {


    val uiState: StateFlow<TaskUiState> = getTasksUseCase().map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskUiState.Loading)


    private val _showDialog =MutableLiveData<Boolean>(false)
    val showDialog:LiveData<Boolean> = _showDialog

    private val _myTaskDialect = MutableLiveData<String>("")
    val myTaskDialect:LiveData<String> = _myTaskDialect

    private val _tasks = mutableStateListOf<TaskModel>()
    val tasks: List<TaskModel> = _tasks


    fun closeDialog(){
        _showDialog.value = false
    }

    fun openDialog(){
        _showDialog.value = true
    }

    fun taskCreated(){
        closeDialog()
        //Log.i("dam2", _myTaskDialect.value?:"")
        _tasks.add(TaskModel(task = _myTaskDialect.value?:""))
        _myTaskDialect.value = ""
    }

    fun removeItem(taskModel: TaskModel){
        val task = _tasks.find { it.id==taskModel.id }
        _tasks.remove(task)
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        val index = _tasks.indexOf(taskModel)

        //Si se modifica directamente _tasks[index].selected = true no se recompone el item en el LazyColumn
        //Esto nos ha pasado ya en el proyecto BlackJack... ¿¿os acordáis?? :-(
        //Y es que la vista no se entera que debe recomponerse, aunque realmente si se ha modificado el valor en el item
        //Para solucionarlo y que se recomponga sin problemas en la vista, lo hacemos con un let...

        //El método let toma como parámetro el objeto y devuelve el resultado de la expresión lambda
        //En nuestro caso, el objeto que recibe let es de tipo TaskModel, que está en _tasks[index]
        //(sería el it de la exprexión lambda)
        //El método copy realiza una copia del objeto, pero modificando la propiedad selected a lo contrario
        //El truco está en que no se modifica solo la propiedad selected de tasks[index],
        //sino que se vuelve a reasignar para que la vista vea que se ha actualizado un item y se recomponga.
        _tasks[index] = _tasks[index].let { it.copy(selected = !it.selected) }
    }

    fun changeText(newText:String){
        _myTaskDialect.value = newText
    }
}