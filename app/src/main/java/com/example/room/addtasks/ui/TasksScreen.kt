package com.example.room.addtasks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.room.addtasks.ui.model.TaskModel

@Composable
fun TasksScreen(tasksViewModel: TasksViewModel) {
    val show :Boolean by tasksViewModel.showDialog.observeAsState(false)
    val text : String by tasksViewModel.myTaskDialect.observeAsState(initial = "")
    Box(Modifier.fillMaxSize()) {
        FabDialog(
            modifier = Modifier.align(Alignment.BottomEnd),
            onNewTask ={tasksViewModel.openDialog() }
        )
        AddTasksDialog(
            show = show,
            myTaskText = text,
            onClose = { tasksViewModel.closeDialog() },
            onTaskAdded = { tasksViewModel.taskCreated() },
            onTextChange = {nuevoTexto-> tasksViewModel.changeText(nuevoTexto) }
        )
        TaskList(tasksViewModel = tasksViewModel)
    }
}

@Composable
fun FabDialog(modifier: Modifier, onNewTask: () -> Unit){
    FloatingActionButton(onClick = onNewTask,
        modifier = modifier.padding(16.dp)) {
        Icon( Icons.Filled.Add, contentDescription = "")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTasksDialog(
    show : Boolean,
    myTaskText:String,
    onClose: ()->Unit,
    onTaskAdded:()->Unit,
    onTextChange:(String)->Unit
){
    if (show){
        Dialog(onDismissRequest = { onClose() }) {
            Column (
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ){
                Text(
                    text = "Añade tu tarea",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                    )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(value = myTaskText,
                    onValueChange = {nuevoTexto-> onTextChange(nuevoTexto) },
                    singleLine = true,
                    maxLines = 1)
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = onTaskAdded,
                    modifier = Modifier.fillMaxWidth()
                    ) {
                    Text(text = "Añadir tarea")
                }
            }
        }
    }

}

@Composable
fun TaskList(tasksViewModel: TasksViewModel){
    val mytasks : List<TaskModel> = tasksViewModel.tasks
    LazyColumn{
        items(mytasks, key = {it.id}) {task ->
            ItemTask(
                taskModel = task,
                onTaskRemove = { tasksViewModel.removeItem(it) },
                onTaskCheckChanged = {tasksViewModel.onCheckBoxSelected(it)}
            )
        }
    }
}

@Composable
fun ItemTask(
    taskModel: TaskModel,
    onTaskRemove : (TaskModel)->Unit,
    onTaskCheckChanged : (TaskModel)->Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = { onTaskRemove(taskModel) })
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row (
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = taskModel.task,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f)
                )
            Checkbox(checked = taskModel.selected, onCheckedChange = {onTaskCheckChanged(taskModel)})

        }
    }
}